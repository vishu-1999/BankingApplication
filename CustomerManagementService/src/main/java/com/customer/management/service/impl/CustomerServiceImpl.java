package com.customer.management.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.management.configuration.CustomerConfiguration;
import com.customer.management.entity.CustomerEntity;
import com.customer.management.exception.CustomerNotFoundException;
import com.customer.management.repository.CustomerRepository;
import com.customer.management.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
	
    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private CustomerConfiguration customerConfiguration;

    @Override
    public CustomerEntity create(CustomerEntity customerEntity) {
            String userId = UUID.randomUUID().toString();
            customerEntity.setCustomerId(userId);
            return customerRepository.save(customerEntity);
        
    }

    @Override
    public List<CustomerEntity> getAll() {
      
            return customerRepository.findAll();
    }
    @Override
    public CustomerEntity getCustomer(String id) {
       Optional<CustomerEntity> customer = customerRepository.findById(id);
       if(!customer.isPresent()) {
    	   throw new CustomerNotFoundException("Customer with given id "+id+" does not exist in DB");
    	   
       }
       
       return customer.get();
    }

    @Override
    public CustomerEntity update(String id, CustomerEntity customerEntity) {
       
            Optional<CustomerEntity> customerOptional = customerRepository.findById(id);

            if (customerOptional.isPresent()) {
                CustomerEntity existingCustomer = customerOptional.get();
                existingCustomer.setName(customerEntity.getName());
                existingCustomer.setPhone(customerEntity.getPhone());
                existingCustomer.setEmail(customerEntity.getEmail());
                existingCustomer.setAddress(customerEntity.getAddress());

                return customerRepository.save(existingCustomer);
            } else {
                throw new CustomerNotFoundException("Customer with given id "+id+" does not exist in DB");
            }
       
    }

    @Override
    public void delete(String id) {
            Optional<CustomerEntity> customerOptional = customerRepository.findById(id);

            if (customerOptional.isPresent()) {
                CustomerEntity customerEntity = customerOptional.get();
                customerConfiguration.deleteAccount(customerEntity.getCustomerId());
                this.customerRepository.delete(customerEntity);
            } else {
                throw new CustomerNotFoundException("Customer with given id "+id+" does not exist in DB");
            }
        
}}
