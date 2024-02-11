package com.luissoy.atmbackend.repository;

import com.luissoy.atmbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}