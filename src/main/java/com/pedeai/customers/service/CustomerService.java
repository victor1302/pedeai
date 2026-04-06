package com.pedeai.customers.service;

import com.pedeai.customers.dto.CreateCostumerDto;
import com.pedeai.customers.entity.Customer;
import com.pedeai.customers.respository.CustomersRepository;
import com.pedeai.security.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final KeycloakService keycloakService;
    private final CustomersRepository customersRepository;

    @Transactional
    public Customer createCustomer(CreateCostumerDto createCostumerDto){
        String keycloakId = keycloakService.createUser(createCostumerDto);


        Customer customer = new Customer();
        customer.setId(UUID.fromString(keycloakId));
        return customersRepository.save(customer);
    }
}
