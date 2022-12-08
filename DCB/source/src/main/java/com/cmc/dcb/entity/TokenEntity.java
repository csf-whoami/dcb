package com.cmc.dcb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_token")
@Setter
@Getter
public class TokenEntity {

	@Id
	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "jwt_token")
	private String jwtToken;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "user_id")
	private int userId;
}
