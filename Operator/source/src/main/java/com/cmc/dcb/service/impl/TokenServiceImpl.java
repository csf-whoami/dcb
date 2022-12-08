package com.cmc.dcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dcb.entity.TokenEntity;
import com.cmc.dcb.repository.TokenRepository;
import com.cmc.dcb.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public TokenEntity generateToken(TokenEntity token) {
		return tokenRepository.save(token);
	}
}
