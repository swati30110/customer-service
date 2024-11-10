package com.example.customerservice.repository;

import com.example.customerservice.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByBillingId(String billingId);
    List<Customer> findByBillingIdIn(List<String> billingIds);
}
