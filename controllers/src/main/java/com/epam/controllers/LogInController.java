package com.epam.controllers;

import com.epam.models.Client;
import com.epam.services.classes.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String logIn(Model model) {
        return "login";
    }

    @PostMapping("/login/authorization")
    public String authorization(@RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Model model)
    {
        Client client = loginService.loginClient(login, password);
        System.out.println("<<< AUTHORIZED CLIENT >>>");
        System.out.println(client);
        return "redirect:/cards";
    }
}
