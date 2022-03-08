package com.epam.controllers.rest.admin.requests;

import com.epam.models.BlockingRequest;
import com.epam.models.Client;
import com.epam.services.conrollers.BlockingRequestService;
import com.epam.services.conrollers.ClientService;
import com.epam.services.conrollers.CreditCardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class BlockingRequestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditCardsService creditCardsService;

    @Autowired
    private BlockingRequestService blockingRequestService;

    @GetMapping("/requests/{page}")
    public Map<Long, BlockingRequest> requests(@PathVariable String page) {
        return blockingRequestService.getRequestsPage(Integer.parseInt(page) - 1).stream()
                .collect(Collectors.toMap(BlockingRequest::getId, Function.identity()));
    }

    @GetMapping("/requests/accept/{id}")
    public String requestAccept(@PathVariable Long id) {
        if (blockingRequestService.requestAlreadyExist(id)) {
            if (blockingRequestService.acceptRequest(id)) {
                log.info("Request with id '{}' has been accepted.", id);
                return String.format("Request with id '%d' has been accepted.", id);
            } else {
                log.info("Request with id '{}' wasn't accepted.", id);
                return String.format("Request with id '%d' wasn't accepted.", id);
            }
        } else {
            log.warn("Request with id '{}' not found.", id);
            return String.format("Request with id '%d' not found.", id);
        }
    }

    @GetMapping("/requests/reject/{id}")
    public String requestReject(@PathVariable Long id) {
        if (blockingRequestService.requestAlreadyExist(id)) {
            if (blockingRequestService.rejectRequest(id)) {
                log.info("Request with id '{}' has been rejected.", id);
                return String.format("Request with id '%d' has been rejected.", id);
            } else {
                log.info("Request with id '{}' wasn't rejected.", id);
                return String.format("Request with id '%d' wasn't rejected.", id);
            }
        } else {
            log.warn("Request with id '{}' not found.", id);
            return String.format("Request with id '%d' not found.", id);
        }
    }
}
