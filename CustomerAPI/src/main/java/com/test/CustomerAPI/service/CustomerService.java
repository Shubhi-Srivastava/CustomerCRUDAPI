package com.test.CustomerAPI.service;
import com.test.CustomerAPI.Entity.Customer;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer findCustomerById(UUID id);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(UUID id, Customer customerDetails);
    void deleteCustomer(UUID id);
}
