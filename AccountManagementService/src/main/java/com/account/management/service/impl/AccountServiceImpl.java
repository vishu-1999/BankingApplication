package com.account.management.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.account.management.configuration.AccountConfiguration;
import com.account.management.entity.Account;
import com.account.management.entity.Customer;
import com.account.management.exception.AccountNotFoundException;
import com.account.management.exception.CustomerNotFoundException;
import com.account.management.exception.DatabaseAccessException;
import com.account.management.exception.DuplicateAccountException;
import com.account.management.exception.InsufficientBalanceException;
import com.account.management.exception.InvalidAmountException;
import com.account.management.exception.NoAccountsLinkedException;
import com.account.management.repository.AccountRepository;
import com.account.management.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

   
    @Autowired
    private AccountConfiguration accountConfiguration;

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account create(Account account) {
    	
        try {
            String accountId = UUID.randomUUID().toString();
            account.setAccountId(accountId);
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateAccountException("Account creation failed. Duplicate account ID.", e);
        } 
    }

    @Override
    public List<Account> getAccounts() {
        try {
            return accountRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException("Error while retrieving accounts from the database.", e);
        } 
    }

    @Override
    public Account getAccount(String id) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);

            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Customer customer = accountConfiguration.getCustomer(account.getCustomerId());
                account.setCustomer(customer);
                return account;
            } else {
                throw new AccountNotFoundException("Account not found with ID: " + id);
            }
        } catch (AccountNotFoundException e) {
            throw e;
        } 
    }
    @Override
    public List<Account> getAccountByCustomerId(String customerId) {
        try {
            Customer customer = accountConfiguration.getCustomer(customerId);
            System.out.println("this is the customer" + customer.getName());
            List<Account> accounts = accountRepository.findByCustomerId(customerId);
            if (accounts.isEmpty()) {
                throw new NoAccountsLinkedException("No accounts Linked with the given customer id");
            }
            return accounts;
        } catch (NoAccountsLinkedException e) {
            // Catch the NoAccountsLinkedException and rethrow it
            throw e;
        } catch (Exception e) {
            // Catch all other exceptions and throw CustomerNotFoundException
            throw new CustomerNotFoundException("Customer with given id " + customerId + " does not exist in DB");
        }
    }

//    @Override
//    public List<Account> getAccountByCustomerId(String customerId) {
//        return accountRepository.findByCustomerId(customerId);
//    }


    @Override  
    public Account addBalance(String id, int amount, String customerId) {
        try {
        	Customer customer = accountConfiguration.getCustomer(customerId);
          Optional<Account> accountOptional = accountRepository.findById(id);
          if (accountOptional.isPresent()) {
            Account newAccount = accountOptional.get();
            Customer customerEntity = accountConfiguration.getCustomer(customerId);

            if (customerEntity == null) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }

            if (amount <= 0) {
                throw new InvalidAmountException("Kindly enter the amount greater than 0");
            }

            int newBalance = newAccount.getBalance() + amount;
            newAccount.setBalance(newBalance);

            return accountRepository.save(newAccount);
        } else {
            throw new AccountNotFoundException("Account with ID " + id + " not found.");
        }

        	
        }catch(AccountNotFoundException e) {
        	throw e;
        }
        catch(InvalidAmountException e) {
        	throw e;
        }
        catch (Exception e) {
            throw new CustomerNotFoundException("Customer with given id " + customerId + " does not exist in DB");

        } 
    }
    
    @Override
    
    public Account withdrawBalance(String id, int amount, String customerId) {
        try {
           
                Customer customerEntity = accountConfiguration.getCustomer(customerId);
                Optional<Account> accountOptional = accountRepository.findById(id);
                if (accountOptional.isPresent()) {
                Account newAccount = accountOptional.get();                
                if (newAccount.getBalance() < amount) {
                    throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
                }
                
                int newBalance = newAccount.getBalance() - amount;
                newAccount.setBalance(newBalance);
               

                return accountRepository.save(newAccount);
            } else {
                throw new AccountNotFoundException("Account with ID " + id + " not found.");
            }
                
        } catch(InsufficientBalanceException e) {
        	throw e;
        }catch(AccountNotFoundException e) {
        	throw e;
        }catch (Exception e) {
            throw new CustomerNotFoundException("Customer with given id " + customerId + " does not exist in DB");
        } 
    }

    @Override
    public void delete(String id) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);

            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                this.accountRepository.delete(account);
            } else {
                throw new AccountNotFoundException("Account with ID " + id + " not found.");
            }
        } catch (AccountNotFoundException e) {
            // Re-throw if it's already an AccountNotFoundException
            throw e;
        } 
    }
    
    @Override
    public void deleteAccountUsingCustomerId(String customerId) {
        try {
            List<Account> accounts = accountRepository.findByCustomerId(customerId);

            for (Account account : accounts) {
                this.accountRepository.delete(account);
            }
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            throw new DatabaseAccessException("Error while deleting accounts from the database.", e);
        } 
    }
	

}

