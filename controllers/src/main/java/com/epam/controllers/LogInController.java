package com.epam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {
    @GetMapping("/login")
    public String logIn(Model model) {
        return "login";
    }

    @PostMapping("/login/authorization")
    public String authorization(@RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Model model)
    {
        System.out.println("-=-=-=-=-< WARNING >-=-=-=-=-");
        System.out.println("|| LOGIN :" + login);
        System.out.println("|| PASSWORD :" + password);
        System.out.println("-=-=-=-=--=-=--=-=-=-=-=-=-=-");
        return "redirect:/cards";
    }
}
