package com.rmm.api.customerServiceMM;

import com.rmm.api.customer.Customer;
import com.rmm.api.customer.CustomerRepository;
import com.rmm.api.serviceMM.ServiceMM;
import com.rmm.api.serviceMM.ServiceMMRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityExistsException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CustomerServiceMMServiceTest {

    public static final Long TEST_ID = 1L;

    @MockBean
    private ServiceMMRepository serviceMMRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerServiceMMRepository customerServiceMMRepository;

    @Autowired
    private CustomerServiceMMService customerServiceMMService;


    private Customer customer;
    private ServiceMM serviceMM;
    private CustomerServiceMM customerServiceMM;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder().id(TEST_ID).name("Test User").build();
        serviceMM = ServiceMM.builder()
                .id(TEST_ID)
                .serviceName("Cloudberry")
                .cost(new BigDecimal("3.00"))
                .selectable(true)
                .build();
        customerServiceMM = CustomerServiceMM.builder()
                .serviceMM(serviceMM)
                .customer(customer)
                .build();
    }

    @Test
    public void shouldAddSelectableServiceToCustomer() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.of(customer));
        given(serviceMMRepository.findByIdAndSelectableIsTrue(anyLong())).willReturn(Optional.of(serviceMM));
        given(customerServiceMMRepository.save(Mockito.any(CustomerServiceMM.class))).willReturn(customerServiceMM);

        CustomerServiceMM result = customerServiceMMService.addService(customer.getId(), serviceMM.getId());

        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getServiceMM()).isEqualTo(serviceMM);
    }

    @Test
    public void shouldAddOnceAvailableServiceToCustomer() {
        given(customerServiceMMRepository.existsByCustomerIdAndServiceMMId(customer.getId(), serviceMM.getId()))
                .willReturn(true);

        assertThrows(EntityExistsException.class,
                () -> customerServiceMMService.addService(customer.getId(), serviceMM.getId()));
    }

}