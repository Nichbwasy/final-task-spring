package com.epam.controllers.rest.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {


    @GetMapping("/")
    public ResponseEntity<String> index() {
        log.debug("Page 'index.html' is loading...");
        return ResponseEntity.ok().body("Hello, this is main page!");
    }
}
