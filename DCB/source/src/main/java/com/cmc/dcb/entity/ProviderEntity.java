package com.cmc.dcb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_provider")
@Setter @Getter
public class ProviderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "service_key")
	private String serviceKey;

	@Column(name = "enabled")
	private boolean enabled;
}
