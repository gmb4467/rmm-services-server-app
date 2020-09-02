package com.rmm.api.cost;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServiceDetail {

    private String serviceName;

    private BigDecimal serviceCost;
}
