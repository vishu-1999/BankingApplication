package com.customer.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.management.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
