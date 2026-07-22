package com.bank.customerservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;
}