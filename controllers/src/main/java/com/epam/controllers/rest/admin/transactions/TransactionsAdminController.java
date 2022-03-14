package com.epam.controllers.rest.admin.transactions;

import com.epam.models.Transaction;
import com.epam.services.conrollers.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TransactionsAdminController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/history/{page}")
    public ResponseEntity<List<Transaction>> transactions(@PathVariable Integer page) {
        return ResponseEntity.ok().body(transactionService.showTransactionHistory(page - 1));
    }

    @GetMapping("/transactions/history/undo/{id}")
    public ResponseEntity<?> transactionsUndo(@PathVariable Long id) {
        transactionService.cancelTransaction(id);
        return ResponseEntity.ok().body(null);
    }
}
