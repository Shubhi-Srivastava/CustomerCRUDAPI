package com.test.CustomerAPI.service;

import com.test.CustomerAPI.Entity.Customer;

import com.test.CustomerAPI.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<Customer> customerDetails = customerRepository.findById(id);

        // Handle the Optional result here
        return customerDetails.orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + id));
    }



    // Creation of a new customer
    @Override
    public Customer addCustomer(Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }


    // Updating details for an existing customer
    @Override
    public Customer updateCustomer(UUID id, Customer customerDetails) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get(); // Retrieve the actual Customer object

            // Update the fields
            existingCustomer.setFirstName(customerDetails.getFirstName());
            existingCustomer.setMiddleName(customerDetails.getMiddleName());
            existingCustomer.setLastName(customerDetails.getLastName());
            existingCustomer.setEmailAddress(customerDetails.getEmailAddress());
            existingCustomer.setPhoneNumber(customerDetails.getPhoneNumber());

            // Save the updated customer back to the repository
            return customerRepository.save(existingCustomer);
        } else {
            throw new NoSuchElementException("Customer not found with ID: " + id);
        }
    }








    // Delete customer
    @Override
    public void deleteCustomer(UUID id) {


        customerRepository.deleteById(id);

    }
}
