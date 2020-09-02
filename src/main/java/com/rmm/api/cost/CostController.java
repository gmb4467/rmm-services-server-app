package com.rmm.api.cost;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CostController {

    private final CostService costService;

    @GetMapping("/{customerId}/cost")
    public CostDetail getCostByCustomer(@PathVariable Long customerId) {
        return costService.getCostDetailByCustomer(customerId);
    }
}
