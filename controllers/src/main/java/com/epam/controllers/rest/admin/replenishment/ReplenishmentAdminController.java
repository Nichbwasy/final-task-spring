package com.epam.controllers.rest.admin.replenishment;

import com.epam.models.Replenishment;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ReplenishmentAdminController {
    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping("/replenishment/history/{page}")
    public Map<Long, Replenishment> replenishment(@PathVariable Integer page) {
        return replenishmentService.showReplenishmentHistory(page - 1).stream()
                .collect(Collectors.toMap(Replenishment::getId, Function.identity()));
    }

    @GetMapping("/replenishment/history/undo/{id}")
    public void replenishmentUndo(@PathVariable Long id) {
        replenishmentService.cancelOperation(id);
    }
}
