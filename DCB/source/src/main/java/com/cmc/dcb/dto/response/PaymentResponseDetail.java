package com.cmc.dcb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PaymentResponseDetail {

	private String msisdn;
	private String refId;
	private String responseCode;
	private String responseDesc;
}
