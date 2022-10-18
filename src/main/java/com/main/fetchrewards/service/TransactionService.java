package com.main.fetchrewards.service;

import com.main.fetchrewards.model.Payer;
import com.main.fetchrewards.model.Transaction;
import com.main.fetchrewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.getTransactions();
    }

    public List<Payer> spendPoints(int spendPoints) {
        List<Payer> payers = new ArrayList<>();

        // map to hold payer as key and total points spent of payer as value
        Map<String, Integer> payerMap = new HashMap<>();

        // get list of transactions with the oldest first 
        List<Transaction> transactions = transactionRepository.getTransactions();

        for (Transaction transaction : transactions) {

            String payer = transaction.getPayer();

            // if payer of current transaction has been seen before, get total points spent of the payer from the map
            int spentPoints = payerMap.getOrDefault(payer, 0);
            int currentPoints = transaction.getPoints();
            int updatePoints;

            if (spendPoints == 0) { // no more points left to spend
                break;
            } else if (spendPoints >= currentPoints) { // if spending balance is greater than points we can spend
                // update map to have total points spent of payer 
                payerMap.put(payer, spentPoints + currentPoints);
                // all points have been spent of current transaction so update to 0
                updatePoints = 0;
                // decrement the spending transaction points since current transaction points have been spent 
                spendPoints = spendPoints - currentPoints;
            } else { // if points we can spend is greater than spending balance
                // update map to have total points spent of payer 
                payerMap.put(payer, spentPoints + spendPoints);
                // get the remainder of the points from the transaction since not all points were spent
                updatePoints = currentPoints - spendPoints;
                // all points have been spent so update to 0 
                spendPoints = 0;
            }
            
            // update the transaction in the database so in the next iteration, we get accurate value of points left available to spend
            transactionRepository.updateTransaction(updatePoints, transaction.getId());
        }

        // convert map of unique payer to points spent for that payer to a list 
        for (String payer : payerMap.keySet()) {
            payers.add(new Payer(payer, -1 * payerMap.get(payer))); // multiply points by -1 to show points have been spent (negative)
        }

        return payers;
    }

    public Map<String, Integer> getBalance() {
        // map to hold payer as key and total points available to spend for payer as value
        Map<String, Integer> balance = new HashMap<>();
        
        transactionRepository.getTransactions().forEach(transaction -> {
            String payer = transaction.getPayer();
            // if payer of current transaction has been seen before, get total points spent of the payer from the map
            int points = balance.getOrDefault(payer, 0);
            // increment points existing for payer with current points and add to balance of payer 
            balance.put(payer, points + transaction.getPoints());
        });
        
        return balance;
    }
}
