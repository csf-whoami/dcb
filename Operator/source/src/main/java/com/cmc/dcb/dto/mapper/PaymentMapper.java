package com.cmc.dcb.dto.mapper;

import java.util.Date;
import java.util.UUID;

import com.cmc.dcb.common.TransactionStatus;
import com.cmc.dcb.dto.CustomerInfo;
import com.cmc.dcb.dto.request.ChargingRequestDetail;
import com.cmc.dcb.dto.request.PaymentRequest;
import com.cmc.dcb.dto.request.PaymentTransactionRequest;
import com.cmc.dcb.dto.request.TransactionInfoRequest;
import com.cmc.dcb.dto.response.ChargingResponse;
import com.cmc.dcb.dto.response.ChargingResponseDetail;
import com.cmc.dcb.dto.response.PaymentResponseDetail;
import com.cmc.dcb.dto.response.TransactionInfoResponse;
import com.cmc.dcb.entity.PaymentTransactionEntity;
import com.cmc.dcb.entity.PaymentTransactionHistoryEntity;

public class PaymentMapper {

	private PaymentMapper() { }

	public static PaymentRequest toPaymentRequest(ChargingRequestDetail charging) {

		PaymentTransactionRequest transactionInfo = new PaymentTransactionRequest();
		transactionInfo.setMsisdn(charging.getCustomerInfo().getMobileNo());
		transactionInfo.setRefId(charging.getTransactionInfo().getTransactionId());
		transactionInfo.setItem(charging.getTransactionInfo().getItem());
		transactionInfo.setItemDescription(charging.getTransactionInfo().getItemDescription());
		transactionInfo.setBalanceType(charging.getTransactionInfo().getBalanceType());
		transactionInfo.setChargeAmount(charging.getTransactionInfo().getAmount());
		transactionInfo.setCurrency(charging.getTransactionInfo().getCurrency());

		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setTransactionInfo(transactionInfo);
		return paymentRequest;
	}

	public static ChargingResponse toChargingResponse(PaymentResponseDetail paymentInfo) {

		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setMobileNo(paymentInfo.getMsisdn());

		TransactionInfoResponse transactionInfo = new TransactionInfoResponse();
		transactionInfo.setTransactionId(paymentInfo.getRefId());
		transactionInfo.setResponseStatus(
				paymentInfo.getResponseCode().trim().equalsIgnoreCase(TransactionStatus.SUCCESS.getCode()) ? TransactionStatus.SUCCESS.getMessage()
						: TransactionStatus.FAILED.getMessage());
		transactionInfo.setResponseDesc(paymentInfo.getResponseDesc());

		ChargingResponseDetail chargingResponseDetail = new ChargingResponseDetail();
		chargingResponseDetail.setCustomerInfo(customerInfo);
		chargingResponseDetail.setTransactionInfo(transactionInfo);

		ChargingResponse chargingResponse = new ChargingResponse();
		chargingResponse.setChargingResponse(chargingResponseDetail);
		return chargingResponse;
	}

	public static PaymentTransactionEntity toPaymentTransaction(PaymentTransactionRequest paymentRequest) {
		PaymentTransactionEntity paymentTransaction = new PaymentTransactionEntity();
		paymentTransaction.setId(String.valueOf(UUID.randomUUID()));
		paymentTransaction.setMsisdn(paymentRequest.getMsisdn());
		paymentTransaction.setTransactionId(paymentRequest.getRefId());
		paymentTransaction.setItem(paymentRequest.getItem());
		paymentTransaction.setItemDescription(paymentRequest.getItemDescription());
		paymentTransaction.setBalanceType(paymentRequest.getBalanceType());
		paymentTransaction.setAmount(paymentRequest.getChargeAmount());
		paymentTransaction.setCurrency(paymentRequest.getCurrency());
		paymentTransaction.setCreatedAt(new Date());
		return paymentTransaction;
	}

	public static PaymentResponseDetail toPaymentResponse(PaymentTransactionEntity transaction) {
		PaymentResponseDetail paymentResponse = new PaymentResponseDetail();
		paymentResponse.setMsisdn(transaction.getMsisdn());
		paymentResponse.setRefId(transaction.getTransactionId());
		return paymentResponse;
	}

	public static PaymentTransactionHistoryEntity toPaymentTransactionHistory(ChargingRequestDetail chargingRequest) {
		TransactionInfoRequest transactionInfo = chargingRequest.getTransactionInfo();
		PaymentTransactionHistoryEntity paymentHistory = new PaymentTransactionHistoryEntity();
		paymentHistory.setId(String.valueOf(UUID.randomUUID()));
		paymentHistory.setTransactionId(transactionInfo.getTransactionId());
		paymentHistory.setItem(transactionInfo.getItem());
		paymentHistory.setItemDescription(transactionInfo.getItemDescription());
		paymentHistory.setBalanceType(transactionInfo.getBalanceType());
		paymentHistory.setAmount(transactionInfo.getAmount());
		paymentHistory.setCurrency(transactionInfo.getCurrency());
		paymentHistory.setMsisdn(chargingRequest.getCustomerInfo().getMobileNo());
		paymentHistory.setCreatedAt(new Date());

		return paymentHistory;
	}

	/**
	 * In case transaction fail.
	 * 
	 * @param chargingRequest
	 * @return
	 */
	public static ChargingResponse toChargingResponse(ChargingRequestDetail chargingRequest) {
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setMobileNo(chargingRequest.getCustomerInfo().getMobileNo());

		TransactionInfoResponse transactionInfo = new TransactionInfoResponse();
		transactionInfo.setTransactionId(chargingRequest.getTransactionInfo().getTransactionId());

		ChargingResponseDetail chargingDetail = new ChargingResponseDetail();
		chargingDetail.setCustomerInfo(customerInfo);
		chargingDetail.setTransactionInfo(transactionInfo);

		ChargingResponse chargingResponse = new ChargingResponse();
		chargingResponse.setChargingResponse(chargingDetail);
		return chargingResponse;
	}
}
