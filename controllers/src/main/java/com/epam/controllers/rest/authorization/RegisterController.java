package com.epam.controllers.rest.authorization;

import com.epam.models.Client;
import com.epam.security.encode.EncoderGenerator;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client", new Client());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registration(@RequestParam(name = "repeat_password") String repeatPassword,
                                     @Valid Client client,
                                     BindingResult bindingResult,
                                     ModelMap modelMap)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successesMessage", "Please, correct data in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
            log.warn("Some data doesn't pass validation!");
        } else if (!clientService.usernameIsFree(client.getUsername())) {
            modelAndView.addObject("successesMessage", String.format("Client with login '%s' already exist!", client.getUsername()));
            log.warn("Client with login '{}' already exist!", client.getUsername());
        } else if (!clientService.emailIsFree(client.getEmail())) {
            modelAndView.addObject("successesMessage", String.format("Email '%s' already exist!", client.getEmail()));
            log.warn("Email '{}' already exist!", client.getEmail());
        } else if (!client.getPassword().equals(repeatPassword)) {
            modelAndView.addObject("successesMessage", "Passwords doesn't match!");
            log.warn("Passwords doesn't match!");
        } else {
            client.setPassword(EncoderGenerator.generateBCryptEncoder().encode(client.getPassword()));
            client = registrationService.registerClient(client);
            if (client != null) {
                modelAndView.addObject("successesMessage", "The client was registered!");
                log.info("The client '{}' was registered!", client);
                modelAndView.setViewName("/");
                return modelAndView;
            } else {
                modelAndView.addObject("successesMessage", "Something went wrong! Can't register a client!");
                log.error("Something went wrong! Can't register a client!");
            }
        }
        modelAndView.addObject("client", new Client());
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
