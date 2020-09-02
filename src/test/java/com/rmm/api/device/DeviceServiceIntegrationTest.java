package com.rmm.api.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeviceServiceIntegrationTest {

//    @Autowired
//    private DeviceRepository deviceRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private DeviceService deviceService;
//
//    @AfterEach
//    public void tearDown() {
//        deviceRepository.deleteAll();
//        customerRepository.deleteAll();
//    }
//
//    @Test
//    public void shouldReturnDevicesByCustomer() {
//        Customer customer = Customer.builder().name("Test Customer").build();
//        Customer customerSaved = customerRepository.save(customer);
//        Device device = Device.builder().customer(customerSaved).systemName("Test System").type("Test Type").build();
//        Device deviceSaved = deviceRepository.save(device);
//        Pageable pageable = PageRequest.of(0, 1);
//
//        Page<Device> result = deviceService.getAllDevicesByCustomer(customerSaved.getId(), pageable);
//
//        assertThat(result.getContent().size()).isEqualTo(1);
//        assertThat(result.getContent().get(0)).isEqualTo(deviceSaved);
//    }
//
//    @Test
//    public void shouldReturnDeviceByCustomerAndId() {
//        Customer customer = Customer.builder().name("Test Customer").build();
//        Customer customerSaved = customerRepository.save(customer);
//        Device device = Device.builder().customer(customerSaved).systemName("Test System").type("Test Type").build();
//        Device deviceSaved = deviceRepository.save(device);
//
//        Device result = deviceService.getDeviceByCustomerAndId(customerSaved.getId(), deviceSaved.getId());
//
//        assertThat(result).isEqualTo(deviceSaved);
//    }
//
//    @Test
//    public void shouldReturnDeviceAdded() {
//        Customer customer = Customer.builder().name("Test Customer").build();
//        customerRepository.save(customer);
//        DeviceRequest deviceRequest = DeviceRequest.builder().systemName("Test System").type("Test Type").build();
//
//        Device result = deviceService.addDevice(deviceRequest, customer.getId());
//
//        assertThat(result.getId()).isNotNull();
//        assertThat(result.getCustomer()).isEqualTo(customer);
//    }
//
//    @Test()
//    public void shouldThrowEntityNotFoundWhenAddDeviceWithNonExistCustomer() {
//        DeviceRequest deviceRequest = DeviceRequest.builder().systemName("Test System").type("Test Type").build();
//
//        assertThrows(EntityNotFoundException.class, () -> deviceService.addDevice(deviceRequest, 1L));
//    }
//
//    @Test
//    public void shouldReturnDeviceUpdated() {
//        Customer customer = Customer.builder().name("Test Customer").build();
//        Customer customerSaved = customerRepository.save(customer);
//        Device device = Device.builder().customer(customerSaved).systemName("Test System").type("Test Type").build();
//        deviceRepository.save(device);
//        DeviceRequest deviceRequest = DeviceRequest.builder().systemName("System Name updated").build();
//
//        Device result = deviceService.updateDevice(customerSaved.getId(), device.getId(), deviceRequest);
//
//        assertThat(result.getSystemName()).isEqualTo(deviceRequest.getSystemName());
//        assertThat(result.getType()).isEqualTo(device.getType());
//    }
//
//    @Test
//    public void shouldThrowEntityNotFoundWhenUpdateNonExistDevice() {
//        DeviceRequest deviceRequest = DeviceRequest.builder().systemName("System Name updated").build();
//
//        assertThrows(EntityNotFoundException.class,
//                () -> deviceService.updateDevice(1L, 1L, deviceRequest));
//    }
//
//    @Test
//    public void shouldDeleteDevice() {
//        Customer customer = Customer.builder().name("Test Customer").build();
//        Customer customerSaved = customerRepository.save(customer);
//        Device device = Device.builder().customer(customerSaved).systemName("Test System").type("Test Type").build();
//        deviceRepository.save(device);
//
//        deviceService.deleteDevice(customerSaved.getId(), device.getId());
//
//        Optional<Device> deviceDeleted = deviceRepository.findById(device.getId());
//        assertThat(deviceDeleted.isPresent()).isFalse();
//    }

}