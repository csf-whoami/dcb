package com.cmc.dcb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dcb.entity.PaymentStatusEntity;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, String> {
}
