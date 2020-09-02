package com.rmm.api.customerServiceMM;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerServiceMMController {

    private final CustomerServiceMMService customerServiceMMService;

    @PostMapping("/{customerId}/services")
    public CustomerServiceMM addService(@PathVariable Long customerId, @RequestBody Long serviceMMId) {
        return customerServiceMMService.addService(customerId, serviceMMId);
    }

    @GetMapping("/{customerId}/services")
    public Page<CustomerServiceMM> getAllServicesByCustomer(@PathVariable Long customerId,
                                                            @PageableDefault(size = 10) Pageable pageable) {
        return customerServiceMMService.getAllServicesByCustomer(customerId, pageable);
    }

    @DeleteMapping("/{customerId}/services/{serviceId}")
    public void deleteService(@PathVariable Long customerId, @PathVariable Long serviceId) {
        customerServiceMMService.deleteService(customerId, serviceId);
    }
}
