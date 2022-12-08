package com.cmc.dcb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dcb.entity.ProviderEntity;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {

	@Query("Select provided from ProviderEntity provided where serviceName = :serviceName")
	Optional<ProviderEntity> findByServiceName(@Param("serviceName") String serviceName);
}
