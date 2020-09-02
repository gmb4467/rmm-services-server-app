package com.rmm.api.device;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{customerId}/devices")
    public Page<Device> getDevicesByCustomer(@PathVariable Long customerId,
                                             @PageableDefault(size = 10) Pageable pageable) {
        return deviceService.getAllDevicesByCustomer(customerId, pageable);
    }

    @GetMapping("/{customerId}/devices/{deviceId}")
    public Device getDevicesByCustomer(@PathVariable Long customerId, @PathVariable Long deviceId) {
        return deviceService.getDeviceByCustomerAndId(customerId, deviceId);
    }

    @PostMapping("/{customerId}/devices")
    public Device addDevice(@PathVariable Long customerId, @RequestBody DeviceRequest deviceRequest) {
        return deviceService.addDevice(deviceRequest, customerId);
    }

    @PutMapping("/{customerId}/devices/{deviceId}")
    public Device updateDevice(@PathVariable Long customerId, @PathVariable Long deviceId,
                               @RequestBody DeviceRequest deviceRequest) {
        return deviceService.updateDevice(customerId, deviceId, deviceRequest);
    }

    @DeleteMapping("/{customerId}/devices/{deviceId}")
    public void deleteDevice(@PathVariable Long customerId, @PathVariable Long deviceId,
                             @RequestBody DeviceRequest deviceRequest) {
        deviceService.deleteDevice(customerId, deviceId);
    }
}
