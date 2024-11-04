package com.test.CustomerAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.CustomerAPI.CustomerApiApplication;
import com.test.CustomerAPI.Entity.Customer;
import com.test.CustomerAPI.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test", classes = CustomerApiApplication.class)

public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/customers" + uri;
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john.doe@example.com");
        customer.setPhoneNumber("1234567890");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(customer), headers);

        ResponseEntity<Customer> response = restTemplate.postForEntity(createURLWithPort(""), entity, Customer.class);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testGetCustomerById() {
        Customer testCustomer = new Customer();
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setEmailAddress("john.doe@example.com");
        testCustomer.setPhoneNumber("1234567890");


        Customer savedCustomer = customerRepository.save(testCustomer);

        ResponseEntity<Customer> response = restTemplate.getForEntity(createURLWithPort("/" + savedCustomer.getUuid()), Customer.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer testCustomer = new Customer();
        testCustomer.setFirstName("Rob");
        testCustomer.setLastName("Smith");
        testCustomer.setEmailAddress("rob.john@example.com");
        testCustomer.setPhoneNumber("28098716789");


        Customer savedCustomer = customerRepository.save(testCustomer);
        UUID customerId = savedCustomer.getUuid();

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("JaneUpdated");
        updatedCustomer.setLastName("DoeUpdated");
        updatedCustomer.setEmailAddress("jane.updated@example.com");
        updatedCustomer.setPhoneNumber("1122334455");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(updatedCustomer), headers);

        ResponseEntity<Customer> response = restTemplate.exchange(createURLWithPort("/" + customerId), HttpMethod.PUT, entity, Customer.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("JaneUpdated", response.getBody().getFirstName());
    }






    @Test
    void testDeleteCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setFirstName("Mark");
        testCustomer.setLastName("Smith");
        testCustomer.setEmailAddress("mark.smith@example.com");
        testCustomer.setPhoneNumber("5566778899");
        customerRepository.save(testCustomer);


        Customer savedCustomer = customerRepository.save(testCustomer);
        UUID customerId = savedCustomer.getUuid();

        ResponseEntity<Void> response = restTemplate.exchange(createURLWithPort("/" + customerId), HttpMethod.DELETE, null, Void.class);

        assertEquals(204, response.getStatusCodeValue());
        assertFalse(customerRepository.existsById(customerId));
    }



}
