package com.pedeai.customers.respository;

import com.pedeai.customers.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
}
