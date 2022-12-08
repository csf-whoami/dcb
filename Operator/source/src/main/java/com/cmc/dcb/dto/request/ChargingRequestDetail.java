package com.cmc.dcb.dto.request;

import com.cmc.dcb.dto.CustomerInfo;

public class ChargingRequestDetail {

	private CustomerInfo customerInfo;
	private TransactionInfoRequest transactionInfo;

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public TransactionInfoRequest getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(TransactionInfoRequest transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

}
