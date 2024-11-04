package com.test.CustomerAPI.repository;
import com.test.CustomerAPI.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;




public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    // Custom query method to find a customer by email address
    Optional<Customer> findByEmailAddress(String emailAddress);

    // Add other custom queries if needed, for example:
    // List<Customer> findByLastName(String lastName);
}
