package com.rmm.api.serviceMM;

import com.rmm.api.operatingSystem.OperatingSystem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service")
public class ServiceMM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String serviceName;

    @Column(columnDefinition = "numeric default 0.00")
    private BigDecimal cost;

    @Column
    private Boolean selectable;

    @ManyToOne
    @JoinColumn(name = "operating_system_id")
    private OperatingSystem operatingSystem;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ServiceMM serviceMM;
}
