package com.luissoy.atmbackend.repository;

import com.luissoy.atmbackend.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    boolean existsBankByName(String name);
}