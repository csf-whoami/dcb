package com.cmc.dcb.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cmc.dcb.entity.ProviderEntity;
import com.cmc.dcb.security.UserPrincipal;

public class UserMapper {

	public static UserPrincipal userToPrincipal(ProviderEntity user) {
		UserPrincipal userp = new UserPrincipal();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		userp.setUsername(user.getServiceName());
		userp.setPassword(user.getServiceKey());
		userp.setEnabled(user.isEnabled());
		userp.setAuthorities(authorities);
		return userp;
	}
}