package com.cmc.dcb.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 4508084040616215039L;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("scope")
	private String scope;
	@JsonProperty("expires_in")
	private int expiresIn; //seconds
	@JsonProperty("token_type")
	private String tokenType;

	public JwtResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}