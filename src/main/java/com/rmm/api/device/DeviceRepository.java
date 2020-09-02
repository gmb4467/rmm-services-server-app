package com.rmm.api.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {

    Page<Device> findByCustomerId(Long customerId, Pageable pageable);

    List<Device> findByCustomerId(Long customerId);

    Long countByCustomerId(Long customerId);

    Optional<Device> findByCustomerIdAndId(Long customerId, Long id);
}
