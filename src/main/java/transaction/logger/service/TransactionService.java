package transaction.logger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.logger.persistence.domain.Transaction;
import transaction.logger.persistence.repository.TransactionRepository;
import transaction.logger.web.TransactionRestDTO;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    
    @Autowired
    public TransactionService(final TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    } 
    
    public ResponseEntity<Void> handleIncomingTransaction(final TransactionRestDTO transactionRestDTO){
        
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
