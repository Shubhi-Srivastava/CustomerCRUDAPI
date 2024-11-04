package com.test.CustomerAPI.controller;
import com.test.CustomerAPI.Entity.Customer;
import com.test.CustomerAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        logger.info("Creating customer: {}", customer);
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }


    @GetMapping
    public List<Customer> getAllCustomers() {
        logger.info("Getting all customers");
        return customerService.findAllCustomers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        logger.info("Received request to retrieve customer with ID: {}", id);
        Customer customer = customerService.findCustomerById(id); // Will throw exception if not found

        return ResponseEntity.ok(customer);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customerDetails) {
        logger.info("Updating customer with ID: {}", id);
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return  ResponseEntity.ok(updatedCustomer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        logger.info("Deleting customer with ID: {}", id);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}


