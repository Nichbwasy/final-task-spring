package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
public class CardsController {

    @Autowired
    private CreditCardsService creditCardsService;

    @RequestMapping("/cards")
    public String cards(Principal principal, Model model) {
        System.out.println("Current client is: " + principal.getName());

        Client client = creditCardsService.getClientByUserName(principal.getName());
        if (client != null) {
            log.debug("Client '{}' was got from credit cards services.", client);
            model.addAttribute("client", client);
        }
        log.debug("Page 'cards.html' is loading...");
        return "cards";
    }

}
