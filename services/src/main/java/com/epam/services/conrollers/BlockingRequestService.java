package com.epam.services.conrollers;

public interface BlockingRequestService {
    Boolean requestAlreadyExist(String cardNumber);
    void sendRequest(String cardNumber);
}
