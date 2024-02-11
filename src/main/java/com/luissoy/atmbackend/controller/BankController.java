package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping
    public List<Bank> getBanks() {
        return bankService.getBanks();
    }

    @PostMapping
    public ResponseEntity<?> saveBank(@RequestBody Bank bank) {
        try {
            Bank savedBank = bankService.saveBank(bank);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(savedBank);
        } catch (DataIntegrityException e) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(new StandardError(e.getMessage(), "POST /api/v1/banks"));
        }
    }
}
