package com.pedeai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TesteController {
    @GetMapping("/public/hello")
    public String publicHello(){
        return "Hello world public";
    }
    @GetMapping("/private/hello")
    public String privateHello(){
        return "Hello world private";
    }
}
