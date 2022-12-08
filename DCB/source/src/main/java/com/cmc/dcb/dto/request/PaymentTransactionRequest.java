package com.cmc.dcb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PaymentTransactionRequest {

	private String msisdn;
	private String refId;
	private String item;
	private String itemDescription;
	private String balanceType;
	private String chargeAmount;
	private String currency;
}
