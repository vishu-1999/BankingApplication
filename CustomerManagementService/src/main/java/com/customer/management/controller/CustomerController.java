package com.customer.management.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.customer.management.entity.CustomerEntity;
import com.customer.management.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create
    @PostMapping
    public ResponseEntity<CustomerEntity> createUser(@RequestBody CustomerEntity customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
    }


    // Get all

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<CustomerEntity>> getCustomers(){
        return ResponseEntity.ok(customerService.getAll());
    }


    // Get one
    @GetMapping("/getCustomer/{customerId}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable("customerId") String customerId){

        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(customerId));

    }

    //delete
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") String customerId)
    {

        this.customerService.delete(customerId);
        String msg = "Customer deleted successfully!";
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    //update

    @PutMapping("updateCustomer/{customerId}")
    public ResponseEntity<CustomerEntity> updateCustomer(@RequestBody CustomerEntity customerEntity, @PathVariable("customerId") String customerId)
    {

        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(customerId, customerEntity));

    }

}

