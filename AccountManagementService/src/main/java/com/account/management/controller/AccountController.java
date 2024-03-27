package com.account.management.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.management.entity.Account;
import com.account.management.exception.DuplicateAccountException;
import com.account.management.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    //Create Account
    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) 
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    // Get single Account Details with customer details
    @GetMapping("/getAccount/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") String accountId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(accountId));
    }

    // Get Accounts using Customer ID

    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<Account>> getAccountsUsingCustomerID(@PathVariable("customerId") String customerId)
    {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(customerId));
    }

    // Get all Account Details

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAccounts()
    {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    // update account

   

    // Add Money
    @PutMapping("/addMoney/{accountId}")
    public ResponseEntity<Account> addMoney(@PathVariable("accountId") String accountId,@RequestParam("amount") int amount,  @RequestParam("customerId") String customerId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.addBalance(accountId,amount, customerId));
    }


    // withdraw Money
    @PutMapping("/withdrawMoney/{accountId}")
    public ResponseEntity<Account> withdraw(@PathVariable("accountId") String accountId,@RequestParam("amount") int amount, @RequestParam("customerId") String customerId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.withdrawBalance(accountId,amount, customerId));
    }

    // Delete Account

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountId") String accountId)
    {
        this.accountService.delete(accountId);
		 return ResponseEntity.status(HttpStatus.OK).body("Account is deleted successfully!!");
    }

    // Delete Account using customerId

    @DeleteMapping("user/{customerId}")
    public void deleteAccountByUserId(@PathVariable("customerId") String customerId)
    {
        this.accountService.deleteAccountUsingCustomerId(customerId);

    }

}

