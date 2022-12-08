package com.cmc.dcb.dto.response;

import com.cmc.dcb.dto.CustomerInfo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ChargingResponseDetail {

	private CustomerInfo customerInfo;
	private TransactionInfoResponse transactionInfo;
}
