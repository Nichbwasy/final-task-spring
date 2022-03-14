package com.epam.controllers.rest.authorization;

import com.epam.config.security.utils.EncoderGenerator;
import com.epam.dto.authorization.RegisterFormDto;
import com.epam.models.Client;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Client> register(@RequestBody RegisterFormDto formData) {
        Client client = registrationService.registerClient(
                formData.getUsername(),
                EncoderGenerator.generateBCryptEncoder().encode(formData.getPassword()),
                formData.getRepeatPassword(),
                formData.getEmail(),
                formData.getFirstName(),
                formData.getLastName()
        );
        return ResponseEntity.ok().body(client);
    }
}
