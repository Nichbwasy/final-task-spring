package com.epam.controllers.rest.authorization;

import com.epam.models.Client;
import com.epam.models.Role;
import com.epam.roles.Roles;
import com.epam.security.encode.EncoderGenerator;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String register() {
        return "Register page.";
    }

    @PostMapping("/register")
    public String register(@RequestParam MultiValueMap<String, String> formData) {
        String username = formData.getFirst("username");
        String password = formData.getFirst("password");
        String repeatPassword = formData.getFirst("repeatPassword");
        String email = formData.getFirst("email");
        String firstName = formData.getFirst("firstName");
        String lastName = formData.getFirst("lastName");
        if (clientService.usernameIsFree(username)) {
            if (password.equals(repeatPassword)) {
                if (clientService.emailIsFree(email)) {
                    String encryptedPassword = EncoderGenerator.generateBCryptEncoder().encode(password);
                    Role role = new Role(Roles.CLIENT);
                    Client client = new Client(username, encryptedPassword, email, firstName, lastName, role);
                    registrationService.registerClient(client);
                    log.info("Client '{}' has been registered successfully!", client);
                    return "Client has been registered successfully!";
                } else {
                    log.warn("Email '{}' already exist!", email);
                    return String.format("Email '%s' already exist", email);
                }
            } else {
                log.warn("Passwords doesn't match!");
                return "Passwords doesn't match!";
            }
        } else {
            log.warn("Username '{}' already exist!", username);
            return String.format("Username '%s' already exist", username);
        }

    }
}
