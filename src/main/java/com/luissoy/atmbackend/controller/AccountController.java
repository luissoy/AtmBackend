package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.luissoy.atmbackend.model.Account;
import com.luissoy.atmbackend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAccounts() { return accountService.getAccounts(); }

    @PostMapping("/{userId}/{bankId}")
    public ResponseEntity<?> saveAccount(@PathVariable(value = "userId") Long userId,
                                         @PathVariable(value = "bankId") Long bankId,
                                         @RequestBody Account account) {
        try {
            Account savedAccount = accountService.saveAccount(account, userId, bankId);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(savedAccount);
        } catch (DataIntegrityException e) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(new StandardError(e.getMessage(), "POST /api/v1/accounts"));
        }
    }
}
