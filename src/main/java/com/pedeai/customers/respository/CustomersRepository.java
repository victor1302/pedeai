package com.pedeai.customers.respository;

import com.pedeai.customers.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
}
