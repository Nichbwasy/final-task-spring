package com.epam.services.conrollers;

import com.epam.models.BlockingRequest;

import java.util.List;

public interface BlockingRequestService {
    List<BlockingRequest> getRequestsPage(Integer page);
    Boolean requestAlreadyExist(String cardNumber);
    Boolean requestAlreadyExist(Long id);
    Boolean acceptRequest(Long id);
    Boolean rejectRequest(Long id);
    void sendRequest(String cardNumber);
}
