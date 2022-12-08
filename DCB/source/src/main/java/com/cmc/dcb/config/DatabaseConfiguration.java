package com.cmc.dcb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.cmc.dcb.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
