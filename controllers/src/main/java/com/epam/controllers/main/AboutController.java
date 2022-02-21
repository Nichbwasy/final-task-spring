package com.epam.controllers.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AboutController.class);

    @GetMapping("/about")
    public String about(Model model) {
        LOGGER.debug("Page 'about.html' is loading...");
        return "about";
    }

}
