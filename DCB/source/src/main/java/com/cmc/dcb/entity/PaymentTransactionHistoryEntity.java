package com.cmc.dcb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_payment_transaction_history")
@Setter @Getter
public class PaymentTransactionHistoryEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "item")
	private String item;

	@Column(name = "item_description")
	private String itemDescription;

	@Column(name = "balance_type")
	private String balanceType;

	@Column(name = "amount")
	private String amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "agent_info")
	private String agentInfo;

	@Column(name = "request_url")
	private String requestUrl;
}
