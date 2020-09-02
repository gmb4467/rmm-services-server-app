package com.rmm.api.serviceMM;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceMMRepository extends PagingAndSortingRepository<ServiceMM, Long> {
    Optional<ServiceMM> findByIdAndSelectableIsTrue(Long id);

    Optional<ServiceMM> findByServiceMMIdAndOperatingSystemId(Long parentId, Long operatingSystemId);

    Optional<ServiceMM> findByServiceName(String serviceName);

}
