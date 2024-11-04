package com.test.CustomerAPI.service;

import com.test.CustomerAPI.Entity.Customer;
import com.test.CustomerAPI.exception.CustomerNotFoundException;
import com.test.CustomerAPI.exception.DuplicateEmailException;
import com.test.CustomerAPI.exception.InvalidCustomerDataException;
import com.test.CustomerAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerCrudService implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerCrudService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    // Retrieve all customers
    @Override
    public List<Customer> findAllCustomers() {

        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }


    // Find customer by their id
    @Override
    public Customer findCustomerById(UUID id) {

        Customer customerDetails =  customerRepository.findById(id)
                .orElseThrow(() -> {
                    return new CustomerNotFoundException("Customer with ID " + id + " does not exist");
                });

        return customerDetails;
    }


    // Creation of a new customer
    @Override
    public Customer addCustomer(Customer customer) {
        if (customer == null) {

            throw new InvalidCustomerDataException("Customer cannot be null");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {

            throw new InvalidCustomerDataException("First name is required");
        }
        if (customer.getLastName() == null || customer.getLastName().isEmpty()) {

            throw new InvalidCustomerDataException("Last name is required");
        }
        if (customer.getEmailAddress() == null || customer.getEmailAddress().isEmpty()) {

            throw new InvalidCustomerDataException("Email address is required");
        }
        if (customerRepository.findByEmailAddress(customer.getEmailAddress()).isPresent()) {

            throw new DuplicateEmailException("Email address is already in use");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty()) {

            throw new InvalidCustomerDataException("Phone number is required");
        }
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }


    // Updating details for an existing customer
    @Override
    public Customer updateCustomer(UUID id, Customer customerDetails) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " does not exist"));


        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setMiddleName(customerDetails.getMiddleName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmailAddress(customerDetails.getEmailAddress());
        existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return updatedCustomer;
    }


    // Delete customer
    @Override
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist");
        }

        customerRepository.deleteById(id);

    }
}
