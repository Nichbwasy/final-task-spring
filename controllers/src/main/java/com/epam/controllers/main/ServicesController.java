package com.epam.controllers.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServicesController.class);

    @GetMapping("/services")
    public String services(Model model) {
        LOGGER.debug("Page 'services.html' is loading...");
        return "services";
    }
}
