package com.bank.customerservice.service;

import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.exception.DuplicateResourceException;
import com.bank.customerservice.exception.ResourceNotFoundException;
import com.bank.customerservice.repository.CustomerRepository;
import com.bank.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        log.info("Creating customer with email: {}", request.getEmail());

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (customerRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        Customer customer = Customer.builder()
                .customerName(request.getCustomerName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created successfully with ID {}", savedCustomer.getCustomerId());

        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {

        log.info("Fetching customer {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID " + customerId));

        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        log.info("Fetching all customers");

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {

        log.info("Updating customer {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID " + customerId));

        customer.setCustomerName(request.getCustomerName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        log.info("Customer updated successfully");

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {

        log.info("Deleting customer {}", customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with ID " + customerId));

        customerRepository.delete(customer);

        log.info("Customer deleted successfully");
    }

    private CustomerResponse mapToResponse(Customer customer) {

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
}