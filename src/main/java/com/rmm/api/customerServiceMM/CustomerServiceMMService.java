package com.rmm.api.customerServiceMM;

import com.rmm.api.customer.Customer;
import com.rmm.api.customer.CustomerRepository;
import com.rmm.api.serviceMM.ServiceMM;
import com.rmm.api.serviceMM.ServiceMMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CustomerServiceMMService {

    private final CustomerRepository customerRepository;

    private final ServiceMMRepository serviceMMRepository;

    private final CustomerServiceMMRepository customerServiceMMRepository;

    public CustomerServiceMM addService(Long customerId, Long serviceMMId) {
        validateServiceAlreadyAdded(customerId, serviceMMId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        ServiceMM serviceMM = serviceMMRepository.findByIdAndSelectableIsTrue(serviceMMId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found or unavailable"));
        CustomerServiceMM customerServiceMM = CustomerServiceMM.builder()
                .customer(customer)
                .serviceMM(serviceMM).build();
        return customerServiceMMRepository.save(customerServiceMM);
    }

    public Page<CustomerServiceMM> getAllServicesByCustomer(Long customerId, Pageable pageable) {
        return customerServiceMMRepository.findByCustomerId(customerId, pageable);
    }

    public void deleteService(Long customerId, Long serviceId) {
        customerServiceMMRepository.deleteCustomerServiceMMByCustomerIdAndServiceMMId(customerId, serviceId);
    }

    private void validateServiceAlreadyAdded(Long customerId, Long serviceMMId) {
        if (customerServiceMMRepository.existsByCustomerIdAndServiceMMId(customerId, serviceMMId)) {
            throw new EntityExistsException("Service is already selected");
        }
    }
}
