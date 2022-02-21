package com.epam.controllers.authorization;

import com.epam.models.Client;
import com.epam.services.classes.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("client")
public class RegisterController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

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
        if(registrationService.loginIsFree(login)) {
            if (registrationService.emailIsFree(email)) {
                Client client = registrationService.registerClient(login, password, email, firstName, lastName);
                if (client != null) {
                    model.addAttribute("client", client);
                    LOGGER.info("New client '{}' has been registered in a system.", client);
                    return "redirect:/cards";
                } else LOGGER.warn("Something went wrong while register a new client!");
            } else LOGGER.warn("Client with email '{}' already exist!", email);
        } else LOGGER.warn("Client with login '{}' already exist!", login);
        return "redirect:/";
    }
}
