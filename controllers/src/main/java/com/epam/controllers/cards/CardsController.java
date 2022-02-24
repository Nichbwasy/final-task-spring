package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Slf4j
@Controller
@SessionAttributes("client")
public class CardsController {

    @Autowired
    private CreditCardsService creditCardsService;

    @RequestMapping("/cards")
    public String cards(Principal principal, Model model) {
        System.out.println("Current user is: " + principal.getName());
        Client user = (Client) model.getAttribute("user");
        log.debug("Client '{}' going to refresh.", user);
        if (user != null) {
            log.debug("Client '{}' was refreshed.", user);
            user = creditCardsService.refreshClient(user);
            model.addAttribute("user", user);
        }
        log.debug("Page 'cards.html' is loading...");
        return "cards";
    }

}
