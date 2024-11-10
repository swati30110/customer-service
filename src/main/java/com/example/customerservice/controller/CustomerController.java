package com.example.customerservice.controller;

import com.example.customerservice.model.dto.CustomerDto;
import com.example.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerDto customerDto) {
        System.out.println(customerDto.toString());
        return customerService.creatCustomer(customerDto);
    }

    @GetMapping("{id}")
    public ResponseEntity getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }
    @PutMapping("{id}")
    public ResponseEntity updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(customerDto);
    }
    @GetMapping("/billing-status")
    public ResponseEntity getCustomersBillStatus(@RequestParam String status){
        return customerService.getCustomersWithDueBills(status);
    }
}
