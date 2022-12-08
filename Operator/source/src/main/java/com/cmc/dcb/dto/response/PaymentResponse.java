package com.cmc.dcb.dto.response;

public class PaymentResponse {
	private PaymentResponseDetail transactionInfo;

	public PaymentResponseDetail getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(PaymentResponseDetail transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
