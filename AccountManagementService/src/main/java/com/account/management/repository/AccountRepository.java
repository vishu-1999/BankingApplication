package com.account.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.account.management.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>  {
    List<Account> findByCustomerId(String customerId);


}
