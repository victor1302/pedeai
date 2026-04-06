package com.pedeai.customers.controller;

import com.pedeai.customers.dto.CreateCostumerDto;
import com.pedeai.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/users")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateCostumerDto createCostumerDto){
        customerService.createCustomer(createCostumerDto);
        return ResponseEntity.ok().build();
    }
}
