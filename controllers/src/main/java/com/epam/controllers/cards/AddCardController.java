package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.services.interfaces.ICreditCardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("client")
public class AddCardController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AddCardController.class);

    @Autowired
    private ICreditCardsService creditCardsService;

    @RequestMapping("/cards/add")
    public String addCard(Model model) {
        return "add-card";
    }

    @PostMapping("/cards/add/adding")
    public String adding(@RequestParam("cardNumber") String cardNumber,
                         @RequestParam("cardMonth") String cardMonth,
                         @RequestParam("cardYear") String cardYear,
                         @RequestParam("cardCVV") String cardCVV,
                         Model model)
    {
        Client client = (Client) model.getAttribute("client");
        if (client != null) {
            if (creditCardsService.addCreditCardToClient(client, cardNumber, cardMonth, cardYear, cardCVV)) {
                LOGGER.info("Credit card has been added to the client '{}' successfully.", client);
            } else {
                LOGGER.warn("Can't add credit card to client!");
            }
        }
        return "redirect:/cards";
    }
}
