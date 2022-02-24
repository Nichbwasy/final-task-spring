package com.epam.controllers.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ServicesController {

    @GetMapping("/services")
    public String services(Model model) {
        log.debug("Page 'services.html' is loading...");
        return "services";
    }
}
