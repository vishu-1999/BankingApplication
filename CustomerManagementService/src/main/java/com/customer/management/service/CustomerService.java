package com.customer.management.service;

import java.util.List; 

import org.springframework.stereotype.Service;

import com.customer.management.entity.CustomerEntity;

@Service
public interface CustomerService {

    // create

    CustomerEntity create(CustomerEntity customer);


    // get all

    List<CustomerEntity> getAll();

    //get single

    CustomerEntity getCustomer(String id);


    //update

    CustomerEntity update(String id, CustomerEntity customer);

    //delete

    void delete(String id);



}
