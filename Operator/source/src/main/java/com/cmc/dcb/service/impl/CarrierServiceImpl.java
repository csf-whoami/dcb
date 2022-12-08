package com.cmc.dcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dcb.common.Constants;
import com.cmc.dcb.common.TransactionStatus;
import com.cmc.dcb.dto.mapper.PaymentMapper;
import com.cmc.dcb.dto.request.PaymentRequest;
import com.cmc.dcb.dto.response.PaymentResponseDetail;
import com.cmc.dcb.entity.PaymentTransactionEntity;
import com.cmc.dcb.repository.PaymentTransactionRepository;
import com.cmc.dcb.service.CarrierService;

@Service
public class CarrierServiceImpl implements CarrierService {

	@Autowired
	private PaymentTransactionRepository paymentRepo;

	@Override
	public PaymentResponseDetail doPayment(PaymentRequest paymentRequest) {

		PaymentTransactionEntity entity = PaymentMapper.toPaymentTransaction(paymentRequest.getTransactionInfo());
		// Insert payment.
		paymentRepo.save(entity);

		PaymentResponseDetail paymentResponse = PaymentMapper.toPaymentResponse(entity);
		paymentResponse.setResponseCode(TransactionStatus.SUCCESS.getCode());
		paymentResponse.setResponseDesc(Constants.SUCCESS_MESSAGE);
		return paymentResponse;
	}
}
