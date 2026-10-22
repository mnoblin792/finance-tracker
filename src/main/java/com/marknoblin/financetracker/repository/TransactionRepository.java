package com.marknoblin.financetracker.repository;

import com.marknoblin.financetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Basic CRUD methods like save(), findAll(), deleteById() are inherited natively.
}

