package transaction.logger.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.logger.cache.CacheUpdateThread;
import transaction.logger.cache.TransactionCache;
import transaction.logger.web.StatisticsRestDTO;
import transaction.logger.web.TransactionRestDTO;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class TransactionService {
    public static final long ONE_MINUTE = Duration.ofMinutes(1L).toMillis();
    private TransactionCache transactionCache;

    @Autowired
    public TransactionService(final TransactionCache transactionCache){
        this.transactionCache = transactionCache;
    }
    
    public ResponseEntity<Void> handleIncomingTransaction(final TransactionRestDTO transactionRestDTO){
        final double amount = transactionRestDTO.getAmount();
        final long timestamp = transactionRestDTO.getTimestamp();
        
        if (amount <= 0 || timestamp <= 0L){
            log.info("Received invalid request body, timestamp or amount may not be negative: {}", transactionRestDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
        }
        
        if (Instant.now().toEpochMilli() - timestamp > ONE_MINUTE){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        
        transactionCache.cacheTransaction(timestamp, amount);
        
        // Could probably better be done with a thread pool
        new CacheUpdateThread(transactionCache, amount, timestamp).start();
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    public ResponseEntity<StatisticsRestDTO> getStatistics(){
        return new ResponseEntity<>(transactionCache.getStatistics(), HttpStatus.OK);
    }
}
