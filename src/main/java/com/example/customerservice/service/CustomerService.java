package com.example.customerservice.service;

import com.example.customerservice.model.dto.CustomerDto;
import com.example.customerservice.model.entity.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity getCustomerById(String id);
    ResponseEntity creatCustomer(CustomerDto customer);
    ResponseEntity updateCustomer(CustomerDto customer);
    ResponseEntity getCustomersWithDueBills(String status);
}
