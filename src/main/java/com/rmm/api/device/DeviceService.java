package com.rmm.api.device;

import com.rmm.api.customer.Customer;
import com.rmm.api.customer.CustomerRepository;
import com.rmm.api.operatingSystem.OperatingSystem;
import com.rmm.api.operatingSystem.OperatingSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    private final CustomerRepository customerRepository;

    private final OperatingSystemRepository operatingSystemRepository;

    public Page<Device> getAllDevicesByCustomer(Long customerId, Pageable pageable) {
        return deviceRepository.findByCustomerId(customerId, pageable);
    }

    public Device getDeviceByCustomerAndId(Long customerId, Long deviceId) {
        return deviceRepository.findByCustomerIdAndId(customerId, deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Data not found "));
    }

    public Device addDevice(DeviceRequest deviceRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        OperatingSystem operatingSystem = operatingSystemRepository.findById(deviceRequest.getOperatingSystemId())
                .orElseThrow(() -> new EntityNotFoundException("Operating System not found"));
        Device deviceToSave = Device.builder().customer(customer).operatingSystem(operatingSystem).build();
        deviceRequest.mergeEditableProperties(deviceToSave);
        return deviceRepository.save(deviceToSave);
    }

    public Device updateDevice(Long customerId, Long deviceId, DeviceRequest deviceRequest) {
        Device deviceToUpdate = deviceRepository.findByCustomerIdAndId(customerId, deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Data not found "));
        OperatingSystem operatingSystem = operatingSystemRepository.findById(deviceRequest.getOperatingSystemId())
                .orElseThrow(() -> new EntityNotFoundException("Operating System not found"));
        deviceRequest.mergeEditableProperties(deviceToUpdate);
        deviceToUpdate.setOperatingSystem(operatingSystem);
        return deviceRepository.save(deviceToUpdate);
    }

    public void deleteDevice(Long customerId, Long deviceId) {
        Device deviceToDelete = deviceRepository.findByCustomerIdAndId(customerId, deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Data not found"));
        deviceRepository.delete(deviceToDelete);
    }

}
