package com.epam.controllers.rest.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogInController {
    @GetMapping("/login")
    public String logIn(Model model) {
        log.debug("Page 'index.html is loading'");
        return "login";
    }

}
