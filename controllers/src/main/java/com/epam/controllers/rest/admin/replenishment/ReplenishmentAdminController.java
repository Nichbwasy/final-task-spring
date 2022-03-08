package com.epam.controllers.rest.admin.replenishment;

import com.epam.models.Replenishment;
import com.epam.services.conrollers.ReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
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

    @PostMapping("/replenishment/history/{page}")
    public String replenishmentUndo(@RequestParam MultiValueMap<String, String> formData) {
        Long replenishmentId = Long.valueOf(formData.getFirst("replenishmentId"));
        if (replenishmentService.cancelOperation(replenishmentId)) {
            log.info("The replenishment operation with id '{}' was canceled.", replenishmentId);
            return String.format("The replenishment operation with id '%d' was canceled.", replenishmentId);
        } else {
            log.warn("The replenishment operation with id '{}' wasn't canceled.", replenishmentId);
            return String.format("The replenishment operation with id '%d' wasn't canceled.", replenishmentId);
        }
    }
}
