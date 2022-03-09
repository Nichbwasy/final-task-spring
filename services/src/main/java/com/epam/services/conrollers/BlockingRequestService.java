package com.epam.services.conrollers;

import com.epam.models.BlockingRequest;
import com.epam.models.Client;

import java.util.List;

public interface BlockingRequestService {
    List<BlockingRequest> getRequestsPage(Integer page);
    Boolean requestAlreadyExist(String cardNumber);
    Boolean requestAlreadyExist(Long id);
    Boolean acceptRequest(Long id);
    Boolean rejectRequest(Long id);
    BlockingRequest sendRequest(Client client, String cardNumber);
}
