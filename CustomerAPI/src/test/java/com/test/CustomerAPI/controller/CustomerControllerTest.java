package com.test.CustomerAPI.controller;

import com.test.CustomerAPI.Entity.Customer;
import com.test.CustomerAPI.controller.CustomerController;
import com.test.CustomerAPI.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setUuid(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john.doe@example.com");
        customer.setPhoneNumber("1234567890");
    }

    @Test
    void testCreateCustomer() {
        when(customerService.addCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.createCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerById() {
        UUID customerId = customer.getUuid();
        when(customerService.findCustomerById(customerId)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
    @Test
    void testUpdateCustomer() {
        UUID customerId = customer.getUuid();
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Doe");
        updatedCustomer.setEmailAddress("jane.doe@example.com");
        updatedCustomer.setPhoneNumber("0987654321");
        when(customerService.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, updatedCustomer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
    }

    @Test
    void testDeleteCustomer() {
        UUID customerId = customer.getUuid();
        doNothing().when(customerService).deleteCustomer(customerId);

        doNothing().when(customerService).deleteCustomer(customerId);

        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}


