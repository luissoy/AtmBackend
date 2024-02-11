package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.service.BankService;
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
class BankControllerTests {

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    @Test
    public void getBanks() {
        // Given
        Bank bank = new Bank();
        bank.setName("bank");
        List<Bank> banks = List.of(bank);

        // When
        when(bankService.getBanks()).thenReturn(banks);

        // Then
        List<Bank> retrievedBanks = bankController.getBanks();
        assertEquals(1, retrievedBanks.size());
    }

    @Test
    public void saveBank_NameExists() {
        // Given
        Bank bank = new Bank();
        bank.setName("existingName");

        // When
        when(bankService.saveBank(bank)).thenThrow(new DataIntegrityException("Bank name already exists"));

        // Then
        ResponseEntity<?> responseEntity = bankController.saveBank(bank);
        assertEquals(StandardError.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void registerBank_NameDoesNotExist() {
        // Given
        Bank bank = new Bank();
        bank.setName("newName");

        // When
        when(bankService.saveBank(bank)).thenReturn(bank);

        // Then
        ResponseEntity<?> responseEntity = bankController.saveBank(bank);
        assertEquals(Bank.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}