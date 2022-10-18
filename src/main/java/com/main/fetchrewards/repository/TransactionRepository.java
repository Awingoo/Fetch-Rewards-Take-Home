package com.main.fetchrewards.repository;

import com.main.fetchrewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t ORDER BY timestamp ASC")
    List<Transaction> getTransactions();
    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.points = :points WHERE t.id = :id")
    void updateTransaction(@Param("points") int points, @Param("id") Long id);
}
