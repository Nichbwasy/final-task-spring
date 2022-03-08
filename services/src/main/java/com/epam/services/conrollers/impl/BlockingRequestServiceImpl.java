package com.epam.services.conrollers.impl;

import com.epam.blocks.BlockTypes;
import com.epam.models.BlockingRequest;
import com.epam.models.CreditCard;
import com.epam.repositories.BlockingRequestRepository;
import com.epam.repositories.CreditCardRepository;
import com.epam.services.conrollers.BlockingRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class BlockingRequestServiceImpl implements BlockingRequestService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BlockingRequestRepository blockingRequestRepository;

    @Override
    public List<BlockingRequest> getRequestsPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return blockingRequestRepository.findAll(pageable).toList();
    }

    @Override
    public Boolean requestAlreadyExist(String cardNumber) {
        return blockingRequestRepository.findByCardNumber(cardNumber).size() > 0;
    }

    @Override
    public Boolean requestAlreadyExist(Long id) {
        return blockingRequestRepository.existsById(id);
    }

    @Override
    @Transactional
    public Boolean acceptRequest(Long id) {
        try {
            BlockingRequest blockingRequest = blockingRequestRepository.getById(id);
            CreditCard creditCard = creditCardRepository.getByCardNumber(blockingRequest.getCardNumber());
            if (blockingRequest.getType().equals(BlockTypes.BLOCK)) creditCard.setIsLocked(Boolean.TRUE);
            else creditCard.setIsLocked(Boolean.TRUE);
            creditCardRepository.save(creditCard);
            log.info("Credit card '{}' was updated.", creditCard.getCardNumber());
            blockingRequestRepository.deleteById(id);
            log.info("Blocking request with id '{}' has been removed.", id);
            return true;
        } catch (Exception e) {
            log.error("Something went wrong while accepting request! ", e);
            return false;
        }
    }


    @Override
    public Boolean rejectRequest(Long id) {
        try {
            blockingRequestRepository.deleteById(id);
            log.info("Blocking request with id '{}' has been removed.", id);
            return true;
        } catch (Exception e) {
            log.error("Something went wrong while accepting request! ", e);
            return false;
        }
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
