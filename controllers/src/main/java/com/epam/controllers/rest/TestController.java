package com.epam.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(Principal principal) {
        return "USER: " + principal.getName() + ". ROLES: ";
    }
}
