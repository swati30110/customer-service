package com.example.customerservice.service;

import com.example.customerservice.exception.RecordNotFoundException;
import com.example.customerservice.model.dto.CustomerDto;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${billing-service.url}")
    private String billingServiceUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public ResponseEntity getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(objectMapper.convertValue(customer.get(), CustomerDto.class));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity creatCustomer(CustomerDto customer) {
        LOGGER.info("Creating Customer detail {} ", customer);
        Customer savedCustomer =customerRepository.save(objectMapper.convertValue(customer, Customer.class));
        URI location = URI.create("/api/customer/" + savedCustomer.getCustomerId());
        LOGGER.info("Bill is Created {}", savedCustomer.toString());
        return ResponseEntity.created(location).body(savedCustomer);
    }

    @Override
    public ResponseEntity updateCustomer(CustomerDto customer) {
        LOGGER.info("Customer detail is updating {}", customer.toString());
        Customer saveCustomer = customerRepository.findById(customer.getCustomerId()).orElseThrow(
                () -> new RecordNotFoundException("Not a valid Billing Id")
        );
        saveCustomer.setCustomerName(customer.getCustomerName());
        saveCustomer.setBillingId(customer.getBillingId());
        Customer updatedCustomer = customerRepository.save(saveCustomer);
        LOGGER.info("Billing record updated with ID: {}", updatedCustomer.getBillingId());
        return ResponseEntity.ok(updatedCustomer);
    }

    public ResponseEntity getCustomersWithDueBills(String status) {
        LOGGER.info("Customer detail with billing status {}", status);
        List<String> dueBillingIds = restTemplate.getForObject(billingServiceUrl + "/billing-status?status=Due", List.class);
        List<Customer> customers = customerRepository.findByBillingIdIn(dueBillingIds);
        return ResponseEntity.ok(customers);
    }
}
