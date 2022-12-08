package com.cmc.dcb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfoResponse {

	private String transactionId;
	private String responseStatus;
	private String responseDesc;
}
