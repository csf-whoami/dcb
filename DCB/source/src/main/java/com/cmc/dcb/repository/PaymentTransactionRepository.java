package com.cmc.dcb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dcb.entity.PaymentTransactionEntity;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, String> {
}
