package com.account.management.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.account.management.entity.Customer;


@FeignClient(name="CUSTOMER-SERVICE")
public interface AccountConfiguration {
	
	@GetMapping("/customer/getCustomer/{customerId}")
	Customer getCustomer(@PathVariable("customerId") String customerId);
	
	
	
}
