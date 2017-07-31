package transaction.logger.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import transaction.logger.cache.TransactionCache;
import transaction.logger.web.TransactionRestDTO;

public class TransactionServiceTest {
    @Mock
    private TransactionCache transactionCacheMock;
    
    private TransactionService transactionService = new TransactionService(transactionCacheMock);
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testInsertCacheHappyCase(){
    }

    @Test
    public void testInsertCacheOldData(){
        
    }

    @Test
    public void testInsertInvalidAmount(){
        
    }

    @Test
    public void testInsertInvalidTimestamp(){
        
    }
}
