package com.cmc.dcb.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmc.dcb.common.Constants;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {

		// Key : service_name - Value : service_key
		CacheConfiguration providedServiceInfo = new CacheConfiguration();
		providedServiceInfo.setName(Constants.CACHE_ALIAS);
		providedServiceInfo.setMemoryStoreEvictionPolicy("LRU");
		providedServiceInfo.setMaxEntriesLocalHeap(1000);
		providedServiceInfo.setTimeToLiveSeconds(3600);

		// Key : service_name - Value : access_token
		CacheConfiguration providedTokenInfo = new CacheConfiguration();
		providedTokenInfo.setName(Constants.CACHE_TOKEN);
		providedTokenInfo.setMemoryStoreEvictionPolicy("LRU");
		providedTokenInfo.setMaxEntriesLocalHeap(1000);
		providedTokenInfo.setTimeToLiveSeconds(3600);

		// Key : mobile_no - Value : service_name
		CacheConfiguration providedMobileInfo = new CacheConfiguration();
		providedMobileInfo.setName(Constants.CACHE_MOBILE);
		providedMobileInfo.setMemoryStoreEvictionPolicy("LRU");
		providedMobileInfo.setMaxEntriesLocalHeap(1000);
		providedMobileInfo.setTimeToLiveSeconds(3600);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(providedServiceInfo);
		config.addCache(providedTokenInfo);
		config.addCache(providedMobileInfo);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
}