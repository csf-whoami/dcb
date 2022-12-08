package com.cmc.dcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dcb.dto.mapper.UserMapper;
import com.cmc.dcb.entity.ProviderEntity;
import com.cmc.dcb.repository.ProviderRepository;
import com.cmc.dcb.service.ProviderService;

@Service
@Transactional
public class ProviderServiceImpl implements UserDetailsService, ProviderService {

	@Autowired
	private ProviderRepository providerRepository;

	@Override
	public UserDetails loadUserByUsername(String serviceName) throws UsernameNotFoundException {

		ProviderEntity user = providerRepository.findByServiceName(serviceName).orElse(null);
		if(user == null) {
			throw new UsernameNotFoundException("Not found service with name " + serviceName);
		}
//				.orElseThrow(() -> new UsernameNotFoundException("User NOT Found"));
		return UserMapper.userToPrincipal(user);
	}
}
