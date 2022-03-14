package com.epam.controllers.rest.admin.replenishment;

import com.epam.models.Replenishment;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ReplenishmentAdminController {
    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping("/replenishment/history/{page}")
    public ResponseEntity<List<Replenishment>> replenishment(@PathVariable Integer page) {
        return ResponseEntity.ok().body(replenishmentService.showReplenishmentHistory(page - 1));
    }

    @GetMapping("/replenishment/history/undo/{id}")
    public ResponseEntity<?> replenishmentUndo(@PathVariable Long id) {
        replenishmentService.cancelOperation(id);
        return ResponseEntity.ok().body(null);
    }
}
