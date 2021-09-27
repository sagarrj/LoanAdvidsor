package com.finance.LoanAdvisor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.finance.LoanAdvisor.entities"})
public class DbConfig {
}
