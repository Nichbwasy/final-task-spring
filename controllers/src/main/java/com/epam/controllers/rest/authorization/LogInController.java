package com.epam.controllers.rest.authorization;

import com.epam.dto.authorization.LoginFormDto;
import com.epam.services.conrollers.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogInController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginFormDto loginForm) throws AuthenticationException {
        return ResponseEntity.ok().body(null);
    }

    //! Test !
    @GetMapping("/login")
    public String logIn(){
        return "This is login page!";
    }

}
