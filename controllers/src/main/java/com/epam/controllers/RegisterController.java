package com.epam.controllers;

import com.epam.models.Client;
import com.epam.services.classes.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {


    @Autowired
    private RegistrationService registrationService;

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
        Client client = registrationService.registerClient(login, password, email, firstName, lastName);
        System.out.println("<<< REGISTERED CLIENT >>>");
        System.out.println(client);
        return "redirect:/cards";
    }
}
