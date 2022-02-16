package com.epam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/register/registration")
    public String registration(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("repeat_password") String repeatPassword,
                               @RequestParam("email") String email,
                               @RequestParam("first_name") String firstName,
                               @RequestParam("last_name") String lastName,
                               Model model
    ) {
        System.out.println("-=-=-=-=-< WARNING >-=-=-=-=-");
        System.out.println("|| LOGIN :" + login);
        System.out.println("|| PASSWORD :" + password);
        System.out.println("|| REPEAT PASSWORD :" + repeatPassword);
        System.out.println("|| EMAIL :" + email);
        System.out.println("|| FIRST NAME :" + firstName);
        System.out.println("|| LAST NAME :" + lastName);
        System.out.println("-=-=-=-=--=-=--=-=-=-=-=-=-=-");
        return "redirect:/cards";
    }
}
