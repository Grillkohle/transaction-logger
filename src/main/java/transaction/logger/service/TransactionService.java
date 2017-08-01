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

        final long now = Instant.now().toEpochMilli();
        final long timeout;
        
        if (now - timestamp > ONE_MINUTE){
            // Transaction is older than 60 seconds
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        
        if (now - timestamp < 0L){
            // Transaction is in the future, not supported (at this time)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        //Transaction may have been anywhere between NOW and 60 seconds ago. It must time out in the remaining time
        timeout = ONE_MINUTE - (now - timestamp);
        
        transactionCache.cacheTransaction(timestamp, amount);
        
        
        // Could probably better be done with a thread pool
        new CacheUpdateThread(transactionCache, amount, timestamp, timeout).start();
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    public ResponseEntity<StatisticsRestDTO> getStatistics(){
        return new ResponseEntity<>(transactionCache.getStatistics(), HttpStatus.OK);
    }
}
