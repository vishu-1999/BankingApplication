package com.account.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.account.management.entity.Account;

public interface AccountService {

    //create

    Account create(Account account);

    //get accounts

    List<Account> getAccounts();

    //get single account

    Account getAccount(String id);

    //get single account using customerId

    List<Account> getAccountByCustomerId(String customerId);

    //update Account


    //update Balance

    Account addBalance(String id, int amount, String customerId);
    Account withdrawBalance(String id, int amount, String customerId);

    //delete

    void delete(String id);

    void deleteAccountUsingCustomerId(String customerId);
}