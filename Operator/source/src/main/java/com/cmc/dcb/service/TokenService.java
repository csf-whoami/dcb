package com.cmc.dcb.service;

import com.cmc.dcb.entity.TokenEntity;

public interface TokenService {

	TokenEntity generateToken(TokenEntity token);
}
