package com.bank.customerservice.controller;

import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import com.bank.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request) {

        log.info("Received request to create customer");

        return customerService.createCustomer(request);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable Long customerId) {

        log.info("Received request to fetch customer {}", customerId);

        return customerService.getCustomerById(customerId);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        log.info("Received request to fetch all customers");

        return customerService.getAllCustomers();
    }

    @PutMapping("/{customerId}")
    public CustomerResponse updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request) {

        log.info("Received request to update customer {}", customerId);

        return customerService.updateCustomer(customerId, request);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long customerId) {

        log.info("Received request to delete customer {}", customerId);

        customerService.deleteCustomer(customerId);
    }
}