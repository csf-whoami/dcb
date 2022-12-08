package com.cmc.dcb.dto.response;

import com.cmc.dcb.dto.CustomerInfo;

public class ChargingResponseDetail {

	private CustomerInfo customerInfo;
	private TransactionInfoResponse transactionInfo;

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public TransactionInfoResponse getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(TransactionInfoResponse transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
