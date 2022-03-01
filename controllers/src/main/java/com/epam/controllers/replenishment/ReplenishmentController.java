package com.epam.controllers.replenishment;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@Slf4j
@Controller
public class ReplenishmentController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping("/cards/replenishment")
    public ModelAndView replenishment(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        modelAndView.addObject("client", client);
        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("replenishment");
        return modelAndView;
    }

    @PostMapping("/cards/replenishment/transaction")
    public ModelAndView replenishment(@Valid CreditCard creditCard,
                                      @RequestParam(name = "amount") String amount,
                                      Principal principal,
                                      BindingResult bindingResult,
                                      ModelMap modelMap
    ) {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        if (client != null) {
            modelAndView.addObject("client", client);
            if(bindingResult.hasErrors()) {
                modelAndView.addObject("successMessage", "Please, correct data in form!");
                modelMap.addAttribute("bindingResult", bindingResult);
                log.warn("Some data doesn't pass validation!");
            } else{
                CreditCard replenishmentCreditCard = creditCardsService.getCreditCardByCardNumber(creditCard.getCardNumber());
                if (replenishmentCreditCard.getCardExpirationMonth().equals(creditCard.getCardExpirationMonth())) {
                    if (replenishmentCreditCard.getCardExpirationYear().equals(creditCard.getCardExpirationYear())) {
                        if (replenishmentCreditCard.getCvv().equals(creditCard.getCvv())) {
                            if (replenishmentService.balanceReplenishment(creditCard, new BigDecimal(amount))) {
                                log.warn("Replenishment of the credit card with number '{}' processed successfully!", creditCard.getCardNumber());
                                modelAndView.setViewName("redirect:/cards");
                                return modelAndView;
                            } else {
                                modelAndView.addObject("successMessage",
                                        String.format("Something went wrong while replenishment credit card with number '%s'!", creditCard.getCardNumber()));
                                log.warn("Something went wrong while replenishment credit card with number '{}'!", creditCard.getCardNumber());
                            }
                        } else {
                            log.warn("Credit card with number '{}' has wrong CVV code!", creditCard.getCardNumber());
                            modelAndView.addObject("successMessage", "Wrong CVV code!");
                        }
                    } else {
                        log.warn("Credit card with number '{}' has wrong expiration year!", creditCard.getCardNumber());
                        modelAndView.addObject("successMessage", "Wrong expiration year!");
                    }
                } else {
                    log.warn("Credit card with number '{}' has wrong expiration month!", creditCard.getCardNumber());
                    modelAndView.addObject("successMessage", "Wrong expiration month!");
                }
            }
        }
        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("replenishment");
        return modelAndView;
    }
}
