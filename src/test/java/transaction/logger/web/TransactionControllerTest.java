package transaction.logger.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import transaction.logger.service.TransactionService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {
    @Mock
    private TransactionService transactionServiceMock;
    
    private TransactionController transactionController;
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        transactionController = new TransactionController(transactionServiceMock);
    }
    
    @Test
    public void testPostHappyCase(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(10)
                .timestamp(System.currentTimeMillis())
                .build();
        
        when(transactionServiceMock.handleIncomingTransaction(any())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        final ResponseEntity<Void> response = transactionController.logTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testPostOutdatedData(){
        final TransactionRestDTO transactionRestDTO = TransactionRestDTO.builder()
                .amount(10)
                .timestamp(System.currentTimeMillis())
                .build();

        when(transactionServiceMock.handleIncomingTransaction(any())).thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));

        final ResponseEntity<Void> response = transactionController.logTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
    
    @Test
    public void testPostNoBody(){
        final TransactionRestDTO transactionRestDTO = null;

        when(transactionServiceMock.handleIncomingTransaction(any())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        final ResponseEntity<Void> response = transactionController.logTransaction(transactionRestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
