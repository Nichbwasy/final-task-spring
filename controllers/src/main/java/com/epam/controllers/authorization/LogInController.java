package com.epam.controllers.authorization;

import com.epam.models.Client;
import com.epam.services.classes.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("client")
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
        if (loginService.loginExist(login)) {
            Client client = loginService.loginClient(login, password);
            if (client != null) {
                LOGGER.info("Client '{}' has been authorized in the system", client);
                session.addAttribute("client", client);
                return "redirect:/cards";
            } else LOGGER.warn("Password doesn't match for the client with login '{}'", login);
        } else LOGGER.warn("User with login '{}' doesn't exist!", login);
        return "redirect:/";
    }
}
