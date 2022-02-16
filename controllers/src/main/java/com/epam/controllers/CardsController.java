package com.epam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CardsController {

    @RequestMapping("/cards")
    public String cards(Model model) {
        return "cards";
    }

}
