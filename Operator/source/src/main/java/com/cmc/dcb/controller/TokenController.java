package com.cmc.dcb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dcb.common.Constants;
import com.cmc.dcb.common.UrlConstants;
import com.cmc.dcb.interceptor.JwtProvider;
import com.cmc.dcb.interceptor.JwtResponse;

@RestController
public class TokenController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider tokenProvider;
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	@Value("${app.prefix}")
	private String headerPrefix;

	@GetMapping(value = UrlConstants.URL_TOKEN)
	public ResponseEntity<?> generateToken(@RequestParam(value = Constants.PARAM_SERVICE_KEY) final String serviceKey,
										   @RequestParam(value = Constants.PARAM_SERVICE_NAME) final String serviceName){

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(serviceName, serviceKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String access_token = tokenProvider.generateToken(authentication);

		JwtResponse response = new JwtResponse();
		response.setAccessToken(access_token);
		response.setScope(Constants.TOKEN_SCOPE);
		response.setExpiresIn(jwtExpirationInMs / 1000);
		response.setTokenType(headerPrefix);
		return ResponseEntity.ok(response);
	}
}
