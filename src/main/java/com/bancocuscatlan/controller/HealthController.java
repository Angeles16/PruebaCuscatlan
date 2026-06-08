package com.bancocuscatlan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping()
    public String showHello(){
        LocalDateTime fecha = LocalDateTime.now();
        return "Hello Angel " + fecha + " :)";
    }
}
