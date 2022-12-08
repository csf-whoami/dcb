package com.cmc.dcb.dto.request;

import com.cmc.dcb.dto.CustomerInfo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ChargingRequestDetail {

	private CustomerInfo customerInfo;
	private TransactionInfoRequest transactionInfo;
}
