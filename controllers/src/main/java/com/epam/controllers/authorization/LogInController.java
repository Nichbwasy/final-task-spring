package com.epam.controllers.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class LogInController {
    @GetMapping("/login")
    public String logIn(Model model) {
        log.debug("Page 'index.html is loading'");
        return "login";
    }

}
