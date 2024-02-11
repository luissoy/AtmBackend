package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.model.Account;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.repository.AccountRepository;
import com.luissoy.atmbackend.repository.BankRepository;
import com.luissoy.atmbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void getAccounts() {
        // Given
        Account account = new Account();

        List<Account> accounts = List.of(account);

        // When
        when(accountRepository.findAll()).thenReturn(accounts);

        // Then
        List<Account> retrievedAccounts = accountService.getAccounts();
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
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(bankRepository.findById(1L)).thenReturn(java.util.Optional.of(bank));
        when(accountRepository.save(account)).thenReturn(account);

        // Then
        Account savedAccount = accountService.saveAccount(account, user.getId(), bank.getId());
        assertEquals(user.getId(), savedAccount.getUser().getId());
        assertEquals(bank.getId(), savedAccount.getBank().getId());
    }

    @Test
    public void saveAccount_UserDoesNotExist() {
        // Given
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        Bank bank = new Bank();
        bank.setId(1L);

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(bankRepository.findById(1L)).thenReturn(java.util.Optional.of(bank));

        // Then
        assertThrows(DataIntegrityException.class, () -> accountService.saveAccount(account, user.getId(), bank.getId()));
    }

    @Test
    public void saveAccount_BankDoesNotExist() {
        // Given
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        Bank bank = new Bank();
        bank.setId(1L);

        // When
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(bankRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(DataIntegrityException.class, () -> accountService.saveAccount(account, user.getId(), bank.getId()));
    }

    @Test
    public void saveAccount_UserAndBankDoesNotExist() {
        // Given
        Account account = new Account();
        User user = new User();
        user.setId(1L);
        Bank bank = new Bank();
        bank.setId(1L);

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(bankRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(DataIntegrityException.class, () -> accountService.saveAccount(account, user.getId(), bank.getId()));
    }
}