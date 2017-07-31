package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import web.dto.TransactionRestDTO;

@Slf4j
@RestController
public class TransactionController {

    @RequestMapping(name = "transactions", method = RequestMethod.POST)
    public ResponseEntity<Void> logTransaction(final TransactionRestDTO transactionRestDTO) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
