package com.rmm.api.cost;

import com.rmm.api.customer.Customer;
import com.rmm.api.customer.CustomerRepository;
import com.rmm.api.customerServiceMM.CustomerServiceMM;
import com.rmm.api.customerServiceMM.CustomerServiceMMRepository;
import com.rmm.api.device.Device;
import com.rmm.api.device.DeviceRepository;
import com.rmm.api.operatingSystem.OperatingSystem;
import com.rmm.api.operatingSystem.OperatingSystemRepository;
import com.rmm.api.serviceMM.ServiceMM;
import com.rmm.api.serviceMM.ServiceMMRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(CostServiceIntegrationTest.Configuration.class)
@ActiveProfiles("test")
public class CostServiceIntegrationTest {
    @Autowired
    private CostService costService;

    @Autowired
    private OperatingSystemRepository operatingSystemRepository;

    @Autowired
    private ServiceMMRepository serviceMMRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceMMRepository customerServiceMMRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private OperatingSystem operatingSystemWindows;

    private OperatingSystem operatingSystemMac;

    private Customer customer;

    private ServiceMM psa;

    private ServiceMM antivirus;

    @BeforeEach
    public void setUp() {
        setUpOperatingSystems();
        setUpServices();
        customer = Customer.builder().name("Test Customer").build();
        customerRepository.save(customer);
    }

    @AfterEach
    public void tearDown() {
        deviceRepository.deleteAll();
        customerServiceMMRepository.deleteAll();
        serviceMMRepository.deleteAll();
        operatingSystemRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @TestConfiguration
    public static class Configuration {
        @Bean(initMethod = "migrate")
        public Flyway flyway(DataSource dataSource) {
            return Flyway.configure().dataSource(dataSource).load();
        }
    }

    @Test
    public void shouldReturnTotalCostByCustomerWithADeviceAndPSAService() {
        CustomerServiceMM customerServiceMM = CustomerServiceMM.builder().customer(customer).serviceMM(psa).build();
        customerServiceMMRepository.save(customerServiceMM);
        Device device = Device.builder().customer(customer).type("Windows Workstation").operatingSystem(operatingSystemWindows).build();
        deviceRepository.save(device);

        CostDetail result = costService.getCostDetailByCustomer(customer.getId());

        assertThat(result.getTotalCost()).isEqualTo(new BigDecimal("6.00"));
    }

    @Test
    public void shouldReturnTotalCostByCustomerWithADeviceAndAntivirusService() {
        CustomerServiceMM customerServiceMM = CustomerServiceMM.builder().customer(customer).serviceMM(antivirus).build();
        customerServiceMMRepository.save(customerServiceMM);
        Device desktopComputer = Device.builder().customer(customer).type("Windows Workstation").operatingSystem(operatingSystemWindows).build();
        Device laptop = Device.builder().customer(customer).type("Mac Laptop").operatingSystem(operatingSystemMac).build();
        List<Device> devices = new ArrayList<>() {{
            add(desktopComputer);
            add(laptop);
        }};
        deviceRepository.saveAll(devices);

        CostDetail result = costService.getCostDetailByCustomer(customer.getId());

        assertThat(result.getTotalCost()).isEqualTo(new BigDecimal("20.00"));
    }

    private void setUpOperatingSystems() {
        operatingSystemWindows = OperatingSystem.builder().systemName("Windows").build();
        operatingSystemMac = OperatingSystem.builder().systemName("Mac").build();
        List<OperatingSystem> operatingSystemList = new ArrayList<>() {{
            add(operatingSystemWindows);
            add(operatingSystemMac);
        }};
        operatingSystemRepository.saveAll(operatingSystemList);
    }

    private void setUpServices() {
        ServiceMM registerDevice = ServiceMM.builder().serviceName("Register Device").selectable(false).cost(new BigDecimal("4.00")).build();
        antivirus = ServiceMM.builder().serviceName("Antivirus").selectable(true).cost(BigDecimal.ZERO).build();
        ServiceMM antivirusWindows = ServiceMM.builder()
                .serviceName("Antivirus Windows")
                .selectable(false)
                .cost(new BigDecimal("5.00"))
                .operatingSystem(operatingSystemWindows)
                .serviceMM(antivirus)
                .build();
        ServiceMM antivirusMac = ServiceMM.builder()
                .serviceName("Antivirus Mac")
                .selectable(false)
                .cost(new BigDecimal("7.00"))
                .operatingSystem(operatingSystemMac)
                .serviceMM(antivirus)
                .build();
        psa = ServiceMM.builder().serviceName("PSA").selectable(true).cost(new BigDecimal("2.00")).build();
        List<ServiceMM> serviceMMList = new ArrayList<>() {{
            add(registerDevice);
            add(antivirus);
            add(antivirusWindows);
            add(antivirusMac);
            add(psa);
        }};
        serviceMMRepository.saveAll(serviceMMList);
    }
}