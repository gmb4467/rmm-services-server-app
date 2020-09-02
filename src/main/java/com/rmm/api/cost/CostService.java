package com.rmm.api.cost;

import com.rmm.api.customerServiceMM.CustomerServiceMM;
import com.rmm.api.customerServiceMM.CustomerServiceMMRepository;
import com.rmm.api.device.Device;
import com.rmm.api.device.DeviceRepository;
import com.rmm.api.serviceMM.ServiceMM;
import com.rmm.api.serviceMM.ServiceMMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CostService {
    public static final String REGISTER_DEVICE_SERVICE = "Register Device";

    private final ServiceMMRepository serviceMMRepository;

    private final CustomerServiceMMRepository customerServiceMMRepository;

    private final DeviceRepository deviceRepository;

    public CostDetail getCostDetailByCustomer(Long customerId) {
        List<ServiceMM> serviceMMList = getServicesByCustomer(customerId);
        List<Device> deviceList = deviceRepository.findByCustomerId(customerId);
        List<DeviceDetail> deviceDetailList = deviceList.stream()
                .map(device -> getDeviceDetail(device, serviceMMList)).collect(Collectors.toList());
        BigDecimal totalCost = deviceDetailList.stream().map(DeviceDetail::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return CostDetail.builder().devices(deviceDetailList).totalCost(totalCost).build();
    }


    private DeviceDetail getDeviceDetail(Device device, List<ServiceMM> serviceMMList) {
        List<ServiceDetail> serviceDetailList = serviceMMList.stream().map(service -> getServiceDetail(service, device))
                .collect(Collectors.toList());
        BigDecimal totalCostDevice = serviceDetailList.stream().map(ServiceDetail::getServiceCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return DeviceDetail.builder().type(device.getType()).cost(totalCostDevice).services(serviceDetailList).build();
    }

    private List<ServiceMM> getServicesByCustomer(Long customerId) {
        ServiceMM registerDeviceService = serviceMMRepository.findByServiceName(REGISTER_DEVICE_SERVICE)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        List<ServiceMM> serviceMMList = customerServiceMMRepository.findByCustomerId(customerId)
                .stream().map(CustomerServiceMM::getServiceMM).collect(Collectors.toList());
        serviceMMList.add(registerDeviceService);
        return serviceMMList;
    }

    private ServiceDetail getServiceDetail(ServiceMM service, Device device) {
        if ((BigDecimal.ZERO.compareTo(service.getCost()) == 0) && service.getServiceMM() == null) {
            ServiceMM serviceMM = serviceMMRepository
                    .findByServiceMMIdAndOperatingSystemId(service.getId(), device.getOperatingSystem().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));
            return ServiceDetail.builder().serviceName(serviceMM.getServiceName()).serviceCost(serviceMM.getCost()).build();
        }
        return ServiceDetail.builder().serviceName(service.getServiceName()).serviceCost(service.getCost()).build();
    }
}
