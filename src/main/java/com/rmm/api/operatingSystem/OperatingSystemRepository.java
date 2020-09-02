package com.rmm.api.operatingSystem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatingSystemRepository extends CrudRepository<OperatingSystem, Long> {
}
