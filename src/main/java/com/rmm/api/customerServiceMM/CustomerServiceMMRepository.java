package com.rmm.api.customerServiceMM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerServiceMMRepository extends PagingAndSortingRepository<CustomerServiceMM, Long> {

    Optional<CustomerServiceMM> findByCustomerIdAndServiceMMId(Long customerId, Long serviceMMId);

    Page<CustomerServiceMM> findByCustomerId(Long customerId, Pageable pageable);

    List<CustomerServiceMM> findByCustomerId(Long customerId);

    boolean existsByCustomerIdAndServiceMMId(Long customerId, Long serviceMMId);

    void deleteCustomerServiceMMByCustomerIdAndServiceMMId(Long customerId, Long serviceMMId);
}
