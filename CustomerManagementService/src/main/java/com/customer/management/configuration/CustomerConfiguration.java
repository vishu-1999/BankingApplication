package com.customer.management.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.customer.management.entity.AccountEntity;


@FeignClient(name = "ACCOUNT-SERVICE")
public interface CustomerConfiguration {
	
	@DeleteMapping("/account/user/{accountId}")
	AccountEntity deleteAccount(@PathVariable("accountId") String accountId);
	
}
