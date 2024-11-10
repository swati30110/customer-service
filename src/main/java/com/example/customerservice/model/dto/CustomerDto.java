package com.example.customerservice.model.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String billingId;
}
