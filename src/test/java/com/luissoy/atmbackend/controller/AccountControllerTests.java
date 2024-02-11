package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.Account;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTests {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void getAccounts() {
        // Given
        Account account = new Account();
        List<Account> accounts = List.of(account);

        // When
        when(accountService.getAccounts()).thenReturn(accounts);

        // Then
        List<Account> retrievedAccounts = accountController.getAccounts();
        assertEquals(1, retrievedAccounts.size());
    }

    @Test
    public void saveAccount_UserAndBankExists() {
        // Given
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        Bank bank = new Bank();
        bank.setId(1L);

        // When
        when(accountService.saveAccount(account, user.getId(), bank.getId())).thenReturn(account);

        // Then
        ResponseEntity<?> responseEntity = accountController.saveAccount(user.getId(), bank.getId(), account);
        assertEquals(Account.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void saveAccount_UserOrBankDoesNotExists() {
        // Given
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        Bank bank = new Bank();
        bank.setId(1L);

        // When
        when(accountService.saveAccount(account, user.getId(), bank.getId())).
                thenThrow(new DataIntegrityException());

        // Then
        ResponseEntity<?> responseEntity = accountController.saveAccount(user.getId(), bank.getId(), account);
        assertEquals(StandardError.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}