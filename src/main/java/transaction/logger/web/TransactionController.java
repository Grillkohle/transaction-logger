package transaction.logger.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import transaction.logger.service.TransactionService;

@Slf4j
@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(name = "transactions", method = RequestMethod.POST)
    public ResponseEntity<Void> logTransaction(@RequestBody final TransactionRestDTO transactionRestDTO) {

        log.info("Received transaction: {}", transactionRestDTO);
        return transactionService.handleIncomingTransaction(transactionRestDTO);
    }
}
