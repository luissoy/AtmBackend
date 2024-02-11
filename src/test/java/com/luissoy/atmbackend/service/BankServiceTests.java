package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTests {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void getBanks() {
        // Given
        Bank bank = new Bank();
        bank.setName("bank");

        List<Bank> banks = List.of(bank);

        // When
        when(bankRepository.findAll()).thenReturn(banks);

        // Then
        List<Bank> retrievedBanks = bankService.getBanks();
        assertEquals(1, retrievedBanks.size());
    }

    @Test
    public void saveBank_NameExists() {
        // Given
        Bank bank = new Bank();
        bank.setName("existingName");

        // When
        when(bankRepository.existsBankByName("existingName")).thenReturn(true);

        // Then
        assertThrows(DataIntegrityException.class, () -> bankService.saveBank(bank));
    }

    @Test
    public void saveBank_NameDoesNotExist() {
        // Given
        Bank bank = new Bank();
        bank.setName("newName");

        // When
        when(bankRepository.existsBankByName("newName")).thenReturn(false);
        when(bankRepository.save(bank)).thenReturn(bank);

        // Then
        Bank savedBank = bankService.saveBank(bank);
        assertEquals(bank, savedBank);
    }
}