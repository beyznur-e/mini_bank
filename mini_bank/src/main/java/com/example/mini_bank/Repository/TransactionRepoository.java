package com.example.mini_bank.Repository;

import com.example.mini_bank.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepoository extends JpaRepository<Transaction,Long> {
}
