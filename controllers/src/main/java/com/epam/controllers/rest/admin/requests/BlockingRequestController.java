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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class BlockingRequestController {

    @Autowired
    private BlockingRequestService blockingRequestService;

    @GetMapping("/requests/{page}")
    public ResponseEntity<List<BlockingRequest>> requests(@PathVariable String page) {
        return ResponseEntity.ok().body(blockingRequestService.getRequestsPage(Integer.parseInt(page) - 1));
    }

    @GetMapping("/requests/accept/{id}")
    public ResponseEntity<?> requestAccept(@PathVariable Long id) {
        blockingRequestService.acceptRequest(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/requests/reject/{id}")
    public ResponseEntity<?> requestReject(@PathVariable Long id) {
        blockingRequestService.rejectRequest(id);
        return ResponseEntity.ok().body(null);
    }
}
