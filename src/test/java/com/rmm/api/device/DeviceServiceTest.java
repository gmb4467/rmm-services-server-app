package com.rmm.api.device;

import com.rmm.api.customer.Customer;
import com.rmm.api.customer.CustomerRepository;
import com.rmm.api.operatingSystem.OperatingSystem;
import com.rmm.api.operatingSystem.OperatingSystemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class DeviceServiceTest {
    private static final Long TEST_ID = 1l;

    @Autowired
    private DeviceService deviceService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private OperatingSystemRepository operatingSystemRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    private DeviceRequest deviceRequest;

    @BeforeEach
    public void setUp() {
        deviceRequest = DeviceRequest.builder().operatingSystemId(TEST_ID).type("Windows Server").build();
    }

    @Test
    public void shouldAddDeviceToCustomerSuccessfully() {
        Customer customer = Customer.builder().id(TEST_ID).name("Test Customer").build();
        OperatingSystem operatingSystem = OperatingSystem.builder().id(TEST_ID).systemName("Windows").build();
        Device device = Device.builder().customer(customer).operatingSystem(operatingSystem).build();
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(operatingSystemRepository.findById(operatingSystem.getId())).willReturn(Optional.of(operatingSystem));
        given(deviceRepository.save(any(Device.class))).willReturn(device);

        Device result = deviceService.addDevice(deviceRequest, customer.getId());

        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getOperatingSystem()).isEqualTo(operatingSystem);
    }

    @Test
    public void shouldAThrowEntityNotFoundExceptionWhenCustomerDoesNotExist() {
        given(customerRepository.findById(TEST_ID)).willThrow(new EntityNotFoundException("Data not found"));

        assertThrows(EntityNotFoundException.class, () -> deviceService.addDevice(deviceRequest, TEST_ID));
    }

    @Test
    public void shouldAThrowEntityNotFoundExceptionWhenOperatingSystemDoesNotExist() {
        given(operatingSystemRepository.findById(TEST_ID)).willThrow(new EntityNotFoundException("Operating System not found"));

        assertThrows(EntityNotFoundException.class, () -> deviceService.addDevice(deviceRequest, TEST_ID));
    }

    @Test
    public void shouldReturnDeviceByCustomer() {
        Customer customer = Customer.builder().id(TEST_ID).name("Test Customer").build();
        OperatingSystem operatingSystem = OperatingSystem.builder().id(TEST_ID).systemName("Windows").build();
        Device device = Device.builder().id(TEST_ID).customer(customer).operatingSystem(operatingSystem).build();
        given(deviceRepository.findByCustomerIdAndId(customer.getId(), TEST_ID)).willReturn(Optional.of(device));

        Device result = deviceService.getDeviceByCustomerAndId(customer.getId(), device.getId());

        assertThat(result).isEqualTo(device);
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenDeviceDoesNotExist() {
        given(deviceRepository.findByCustomerIdAndId(TEST_ID, TEST_ID)).willThrow(new EntityNotFoundException("Data not found"));

        assertThrows(EntityNotFoundException.class, () -> deviceService.getDeviceByCustomerAndId(TEST_ID, TEST_ID));
    }

    @Test
    public void shouldUpdateDevice() {
        Long oldOperatingSystemId = 2L;
        String oldType = "Mac Laptop";
        Customer customer = Customer.builder().id(TEST_ID).name("Test Customer").build();
        OperatingSystem oldOperatingSystem = OperatingSystem.builder().id(oldOperatingSystemId).systemName("Mac").build();
        OperatingSystem newOperatingSystem = OperatingSystem.builder().id(TEST_ID).systemName("Windows").build();
        Device device = Device.builder().id(TEST_ID).customer(customer).operatingSystem(oldOperatingSystem).type(oldType).build();
        given(deviceRepository.findByCustomerIdAndId(customer.getId(), device.getId())).willReturn(Optional.of(device));
        given(operatingSystemRepository.findById(deviceRequest.getOperatingSystemId())).willReturn(Optional.of(newOperatingSystem));
        given(deviceRepository.save(any(Device.class))).willReturn(device);

        Device result = deviceService.updateDevice(customer.getId(), device.getId(), deviceRequest);

        assertThat(result.getOperatingSystem().getId()).isEqualTo(deviceRequest.getOperatingSystemId());
        assertThat(result.getType()).isEqualTo(deviceRequest.getType());
    }
}
