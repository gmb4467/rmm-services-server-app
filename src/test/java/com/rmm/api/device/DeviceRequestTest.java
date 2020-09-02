package com.rmm.api.device;

import com.rmm.api.operatingSystem.OperatingSystem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceRequestTest {

    @Test
    public void shouldCopyOnlyEditableProperties() {
        OperatingSystem operatingSystem = OperatingSystem.builder().systemName("Mac").build();
        Device device = Device.builder().operatingSystem(operatingSystem).type("Laptop").build();
        DeviceRequest deviceRequest = DeviceRequest.builder().operatingSystemId(1L).type("Workstation").build();

        deviceRequest.mergeEditableProperties(device);

        assertThat(device.getType()).isEqualTo("Workstation");
    }
}