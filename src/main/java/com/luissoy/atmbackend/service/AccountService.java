package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.model.Account;
import com.luissoy.atmbackend.model.Bank;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.repository.AccountRepository;
import com.luissoy.atmbackend.repository.BankRepository;
import com.luissoy.atmbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    public List<Account> getAccounts() { return accountRepository.findAll(); }

    public Account saveAccount(Account account, Long userId, Long bankId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Bank> bank = bankRepository.findById(bankId);

        if (user.isEmpty() || bank.isEmpty()) {
            throw new DataIntegrityException(
                    (user.isEmpty() ? "User with id = " + userId + " not found. " :
                            "User with id = " + userId + " found. ") +
                            (bank.isEmpty() ? "Bank with id = " + userId + " not found. " :
                                    "Bank with id = " + userId + " found. ")
            );
        }

        account.setUser(user.get());
        account.setBank(bank.get());

        return accountRepository.save(account);
    }
}
