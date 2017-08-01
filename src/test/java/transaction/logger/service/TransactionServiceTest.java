package transaction.logger.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import transaction.logger.cache.TransactionCache;
import transaction.logger.web.TransactionRestDTO;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static transaction.logger.service.TransactionService.ONE_MINUTE;

@Ignore
public class TransactionServiceTest {
    private static final long TWO_MINUTES = ONE_MINUTE * 2L;
    
    @Mock
    private TransactionCache transactionCacheMock;
    
    private TransactionService transactionService;
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionService(transactionCacheMock);
    }
    
    @Test
    public void testInsertCacheHappyCase(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(10)
                .timestamp(System.currentTimeMillis())
                .build();

        final ResponseEntity<Void> response = transactionService.handleIncomingTransaction(transactionRestDTO);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testInsertCacheOldData(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(10)
                .timestamp(System.currentTimeMillis() - TWO_MINUTES)
                .build();

        final ResponseEntity<Void> response = transactionService.handleIncomingTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testInsertInvalidAmount(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(-10)
                .timestamp(System.currentTimeMillis() - TWO_MINUTES)
                .build();

        final ResponseEntity<Void> response = transactionService.handleIncomingTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    
    // Unless we're running this service on or before January 1, 1970, the timestamp will never be negative
    @Test
    public void testInsertInvalidTimestamp(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(10)
                .timestamp(-System.currentTimeMillis())
                .build();

        final ResponseEntity<Void> response = transactionService.handleIncomingTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
