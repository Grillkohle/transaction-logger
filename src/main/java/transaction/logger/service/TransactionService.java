package transaction.logger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.logger.cache.TransactionCache;
import transaction.logger.web.TransactionRestDTO;

@Service
public class TransactionService {
    private TransactionCache transactionCache;
    
    @Autowired
    public TransactionService(final TransactionCache transactionCache){
        this.transactionCache = transactionCache;
    }
    
    public ResponseEntity<Void> handleIncomingTransaction(final TransactionRestDTO transactionRestDTO){
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
