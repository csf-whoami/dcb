package com.cmc.dcb.dto.request;

public class PaymentRequest {

	PaymentTransactionRequest transactionInfo;

	public PaymentTransactionRequest getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(PaymentTransactionRequest transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
