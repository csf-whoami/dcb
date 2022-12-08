package com.cmc.dcb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfoRequest {

	private String transactionId;
	private String item;
	private String itemDescription;
	private String balanceType;
	private String amount;
	private String currency;
}
