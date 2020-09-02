package com.rmm.api.cost;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CostDetail {

    private List<DeviceDetail> devices;

    private BigDecimal totalCost;


}
