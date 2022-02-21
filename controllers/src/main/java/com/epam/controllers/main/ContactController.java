package com.epam.controllers.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/contact")
    public String contact(Model model) {
        LOGGER.debug("Page 'contact.html' is loading...");
        return "contact";
    }
}
