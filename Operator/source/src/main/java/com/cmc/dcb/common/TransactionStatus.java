package com.cmc.dcb.common;

public enum TransactionStatus {

	SUCCESS("00", "Success"), FAILED("11", "Failed");

	private final String code;
	private final String message;

	TransactionStatus(final String code, final String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
