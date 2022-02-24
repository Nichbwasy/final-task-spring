package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@SessionAttributes("client")
public class AddCardController {

    @Autowired
    private CreditCardsService creditCardsService;

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
        Client user = (Client) model.getAttribute("user");
        if (user != null) {
            if (creditCardsService.addCreditCardToClient(user, cardNumber, cardMonth, cardYear, cardCVV)) {
                log.info("Credit card has been added to the user '{}' successfully.", user);
            } else {
                log.warn("Can't add credit card to user!");
            }
        }
        return "redirect:/cards";
    }
}
