package com.epam.controllers.rest.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServicesController {

    @GetMapping("/services")
    public ResponseEntity<String> services() {
        log.debug("Page 'services.html' is loading...");
        return ResponseEntity.ok("Hello, this is services page!");
    }
}
