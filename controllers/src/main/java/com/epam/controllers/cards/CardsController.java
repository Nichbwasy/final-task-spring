package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.services.interfaces.ICreditCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("client")
public class CardsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CardsController.class);

    @Autowired
    private ICreditCardsService creditCardsService;

    @RequestMapping("/cards")
    public String cards(Model model) {
        Client client = (Client) model.getAttribute("client");
        LOGGER.debug("Client '{}' going to refresh.", client);
        if (client != null) {
            LOGGER.debug("Client '{}' was refreshed.", client);
            client = creditCardsService.refreshClient(client);
            model.addAttribute("client", client);
        }
        LOGGER.debug("Page 'cards.html' is loading...");
        return "cards";
    }

}
