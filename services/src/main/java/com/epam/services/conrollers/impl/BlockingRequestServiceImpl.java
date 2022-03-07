package com.epam.services.conrollers.impl;

import com.epam.blocks.BlockTypes;
import com.epam.models.BlockingRequest;
import com.epam.models.CreditCard;
import com.epam.repositories.BlockingRequestRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.BlockingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BlockingRequestServiceImpl implements BlockingRequestService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BlockingRequestRepository blockingRequestRepository;

    @Override
    public Boolean requestAlreadyExist(String cardNumber) {
        return blockingRequestRepository.findByCardNumber(cardNumber).size() > 0;
    }

    @Override
    @Transactional
    public void sendRequest(String cardNumber) {
        CreditCard creditCard = creditCardRepository.getByCardNumber(cardNumber);
        BlockingRequest blockingRequest = new BlockingRequest();
        blockingRequest.setCardNumber(cardNumber);
        if (creditCard.getIsLocked())  blockingRequest.setType(BlockTypes.UNBLOCK);
        else blockingRequest.setType(BlockTypes.BLOCK);
        blockingRequestRepository.save(blockingRequest);
    }
}
