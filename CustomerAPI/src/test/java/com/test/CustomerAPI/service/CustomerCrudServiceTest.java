package com.test.CustomerAPI.service;

import com.test.CustomerAPI.Entity.Customer;
import com.test.CustomerAPI.exception.CustomerNotFoundException;
import com.test.CustomerAPI.exception.DuplicateEmailException;
import com.test.CustomerAPI.exception.InvalidCustomerDataException;
import com.test.CustomerAPI.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerCrudServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerCrudService customerService;

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
    void testAddCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = customerService.addCustomer(customer);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testAddCustomer_MissingFirstName() {
        customer.setFirstName(null);
        assertThrows(InvalidCustomerDataException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testAddCustomer_MissingLastName() {
        customer.setLastName(null);
        assertThrows(InvalidCustomerDataException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test
    void testAddCustomer_Email() {
        customer.setEmailAddress(null);
        assertThrows(InvalidCustomerDataException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }


    @Test
    void testAddCustomer_PhoneNumber() {
        customer.setPhoneNumber(null);
        assertThrows(InvalidCustomerDataException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }


    @Test
    void testAddCustomer_DuplicateEmail() {
        when(customerRepository.findByEmailAddress(customer.getEmailAddress())).thenReturn(Optional.of(customer));
        assertThrows(DuplicateEmailException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository, never()).save(any(Customer.class));
    }


    @Test
    void testAddCustomer_NullCustomer() {
        assertThrows(InvalidCustomerDataException.class, () -> customerService.addCustomer(null));
        verify(customerRepository, never()).save(any(Customer.class));
    }


    @Test
    void testFindCustomerById_Found() {
        UUID customerId = customer.getUuid();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer result = customerService.findCustomerById(customerId);
        assertEquals(customer, result);
    }
    @Test
    void testFindCustomerById_NotFound() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById(customerId));
    }



    @Test
    void testUpdateCustomer() {
        UUID customerId = customer.getUuid();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("UpdatedName");
        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assertEquals("UpdatedName", result.getFirstName());
    }

    @Test
    void testDeleteCustomer() {
        UUID customerId = customer.getUuid();
        when(customerRepository.existsById(customerId)).thenReturn(true);

        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }
}
