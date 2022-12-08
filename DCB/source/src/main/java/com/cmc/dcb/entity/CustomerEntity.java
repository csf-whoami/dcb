package com.cmc.dcb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_customer")
@Setter @Getter
public class CustomerEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "deleted_at")
	private Date deletedAt;
}
