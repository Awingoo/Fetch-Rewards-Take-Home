package com.main.fetchrewards.controller;

import com.main.fetchrewards.model.Payer;
import com.main.fetchrewards.model.Spend;
import com.main.fetchrewards.model.Transaction;
import com.main.fetchrewards.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger log = LogManager.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @GetMapping("/")
    public @ResponseBody List<Transaction> getTransaction() {

        List<Transaction> transactions = new ArrayList<>();

        try {
            transactions = transactionService.getTransactions();
        } catch (Exception e) {
            log.error("Exception while retrieving transactions", e);
        }
        return transactions;

    }

    @PostMapping("/add")
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {

        try {
            transactionService.addTransaction(transaction);
        } catch (Exception e) {
            log.error("Exception while adding transaction", e);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/spend")
    public @ResponseBody List<Payer> spendPoints(@RequestBody Spend spend) {

        List<Payer> payers = null;
        try {
            payers = transactionService.spendPoints(spend.getPoints());
        } catch (Exception e) {
            log.error("Exception while spending points", e);
        }

        return payers;
    }

    @GetMapping("/balance")
    public @ResponseBody Map<String, Integer> getBalance() {
        Map<String, Integer> balance = new HashMap<>();
        try {
            balance = transactionService.getBalance();
        } catch (Exception e) {
            log.error("Exception while getting balance", e);
        }

        return balance;
    }
}
