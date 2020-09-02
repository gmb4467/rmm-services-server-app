package com.rmm.api.cost;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DeviceDetail {

    private String type;

    private List<ServiceDetail> services;

    private BigDecimal cost;
}
