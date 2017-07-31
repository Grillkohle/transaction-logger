package transaction.logger.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.logger.cache.TransactionCache;
import transaction.logger.web.TransactionRestDTO;

@Service
@Slf4j
public class TransactionService {
    private static final long ONE_MINUTE = 60L * 1000L;
    private TransactionCache transactionCache;
    
    @Autowired
    public TransactionService(final TransactionCache transactionCache){
        this.transactionCache = transactionCache;
    }
    
    public ResponseEntity<Void> handleIncomingTransaction(final TransactionRestDTO transactionRestDTO){
        if (transactionRestDTO.getAmount() <= 0 || transactionRestDTO.getTimestamp() <= 0L){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
        }
        
        if (System.currentTimeMillis() - transactionRestDTO.getTimestamp() > ONE_MINUTE){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        
        transactionCache.cacheTransaction(transactionRestDTO.getTimestamp(), transactionRestDTO.getAmount());
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
