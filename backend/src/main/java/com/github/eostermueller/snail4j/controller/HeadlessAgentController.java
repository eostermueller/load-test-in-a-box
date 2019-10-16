package com.github.eostermueller.snail4j.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HeadlessAgentController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}

