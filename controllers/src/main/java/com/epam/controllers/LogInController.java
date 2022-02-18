package com.epam.controllers;

import com.epam.models.Client;
import com.epam.services.classes.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class LogInController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogInController.class);

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String logIn(Model model) {
        return "login";
    }

    @PostMapping("/login/authorization")
    public String authorization(@RequestParam(name = "login") String login,
                                @RequestParam(name = "password") String password,
                                Model session)
    {
        Client client = loginService.loginClient(login, password);
        if (client != null) {
            LOGGER.info("Client '{}' has been authorized in the system", client);
            session.addAttribute("client", client);
            return "/cards";
        } else {
            LOGGER.warn("Client with login '{}' wasn't found! Wrong login or password!", login);
            session.addAttribute("client", null);
            return "redirect:/";
        }
    }
}
