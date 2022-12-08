package com.cmc.dcb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dcb.entity.PaymentTransactionHistoryEntity;

@Repository
public interface PaymentTransactionHistoryRepository extends JpaRepository<PaymentTransactionHistoryEntity, String> {
}
