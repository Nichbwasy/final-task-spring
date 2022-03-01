package com.epam.controllers.transaction;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import com.epam.services.conrollers.TransactionService;
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
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/cards/transaction")
    public ModelAndView transaction(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        modelAndView.addObject("client", client);
        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

    @PostMapping("/cards/transaction/process")
    public ModelAndView replenishment(@Valid CreditCard creditCard,
                                      @RequestParam(name = "amount") String amount,
                                      @RequestParam(name = "recipientCardNumber") String recipientCardNumber,
                                      Principal principal,
                                      BindingResult bindingResult,
                                      ModelMap modelMap
    ) {
        ModelAndView modelAndView = new ModelAndView();
        Client client = clientService.getClientByUsername(principal.getName());
        modelAndView.addObject("client", client);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please, correct data in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
            log.warn("Some data doesn't pass validation!");
        } else {
            if (client.getCreditCards().stream().anyMatch(card -> card.getCardNumber().equals(creditCard.getCardNumber()))) {
                CreditCard senderCard = creditCardsService.getCreditCardByCardNumber(creditCard.getCardNumber());
                BigDecimal amountToSend = new BigDecimal(amount);
                if (senderCard.getBalance().getAmount().compareTo(amountToSend) >= 0) {
                    if (creditCardsService.creditCardAlreadyExist(recipientCardNumber)) {
                        if (transactionService.startTransaction(senderCard.getCardNumber(), recipientCardNumber, amountToSend)) {

                            log.info("Transaction between '{}' and '{}' on amount {} has been completed successfully.", creditCard.getCardNumber(), recipientCardNumber, amount);
                            modelAndView.setViewName("redirect:/cards");
                            return modelAndView;
                        } else {
                            modelAndView.addObject("successMessage","Something went wrong! Transaction wasn't completed!");
                            log.warn("Something went wrong! Transaction wasn't completed!");
                        }
                    } else {
                        modelAndView.addObject("successMessage",String.format("The recipient credit card '%s' not found!", recipientCardNumber));
                        log.warn("The recipient credit card '{}' not found!", recipientCardNumber);
                    }
                } else {
                    modelAndView.addObject("successMessage",String.format("The credit card '%s' don't have enough money to continue transaction!", creditCard.getCardNumber()));
                    log.warn("The credit card '{}' don't have enough money to continue transaction!", creditCard.getCardNumber());
                }
            } else {
                modelAndView.addObject("successMessage",String.format("The credit card with number '%s' doesn't belong to the client '%s'!"
                        , creditCard.getCardNumber(), client.getUsername()));
                log.warn("The credit card with number '{}' doesn't belong to the client '{}'!", creditCard.getCardNumber(), client.getUsername());
            }
        }

        modelAndView.addObject("creditCard", new CreditCard());
        modelAndView.setViewName("transaction");
        return modelAndView;
    }

}
