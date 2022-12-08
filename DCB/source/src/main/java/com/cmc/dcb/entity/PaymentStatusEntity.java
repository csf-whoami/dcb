package com.cmc.dcb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_payment_status")
@Setter @Getter
public class PaymentStatusEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "msisdn")
	private String msisdn;

	@Column(name = "ref_id")
	private String refId;

	@Column(name = "response_code")
	private String responseCode;

	@Column(name = "message_desc")
	private int messageDesc;

	@Column(name = "created_at")
	private int createdAt;
}
