package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public List<Bank> getBanks() { return bankRepository.findAll(); }

    public Bank saveBank(Bank bank) {
        if (bankRepository.existsBankByName(bank.getName())) {
            throw new DataIntegrityException("Name already exists");
        }

        return bankRepository.save(bank);
    }
}
