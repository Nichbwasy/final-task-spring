package com.epam.controllers.cards;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@SessionAttributes("client")
public class AddCardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @GetMapping("/cards/add")
    public ModelAndView addCard(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        modelAndView.addObject("client", client);
        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("add-card");
        return modelAndView;
    }

    @PostMapping("/cards/add/adding")
    public ModelAndView adding(@Valid CreditCard creditCard,
                               Principal principal,
                               BindingResult bindingResult,
                               ModelMap modelMap)
    {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        if (client != null) {
            if(bindingResult.hasErrors()) {
                modelAndView.addObject("successesMessage", "Please, correct data in form!");
                modelMap.addAttribute("bindingResult", bindingResult);
                log.warn("Some data doesn't pass validation!");
            } else if (!creditCardsService.creditCardAlreadyExist(creditCard.getCardNumber())) {
                if (creditCardsService.addCreditCardToClient(client, creditCard)) {
                    log.info("Credit card has been added to the client '{}' successfully.", client);
                    modelAndView.setViewName("redirect:/cards");
                    return modelAndView;
                } else {
                    log.warn("Can't add credit card to client!");
                }
            } else {
                modelAndView.addObject("successesMessage", String.format("Card with number '%s' already exists!", creditCard.getCardNumber()));
            }
            modelMap.addAttribute("client", client);
        }
        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("add-card");
        return modelAndView;
    }
}
