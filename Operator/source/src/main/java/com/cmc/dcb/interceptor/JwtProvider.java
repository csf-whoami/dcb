package com.cmc.dcb.interceptor;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cmc.dcb.entity.TokenEntity;
import com.cmc.dcb.security.UserPrincipal;
import com.cmc.dcb.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	@Value("${app.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	@Autowired
	private TokenService tokenService;

	public String generateToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		String token = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		TokenEntity tokenEntity = new TokenEntity();
		String access_token = String.valueOf(UUID.randomUUID());
		tokenEntity.setAccessToken(access_token);
		tokenEntity.setJwtToken(token);
		tokenEntity.setRefreshToken(token);

		tokenService.generateToken(tokenEntity);
		return access_token;
	}

	public String getUserUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException 
									| ExpiredJwtException
									| UnsupportedJwtException
									| IllegalArgumentException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
