package com.cmc.dcb.interceptor;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public JwtResponse(String accessToken, String scope, int expiresIn, String tokenType) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	public JwtResponse() {
	}
}