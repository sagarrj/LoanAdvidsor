package com.finance.loanadvisor.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author priypawa This configuration class contains {@link ModelMapper} and
 *         {@link MethodValidationPostProcessor} beans
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.finance.LoanAdvisor.entities" })
public class ApplicationConfig {

	/**
	 * This bean method just creates a ModelMapper instance and returns it.
	 * 
	 * @return {@link ModelMapper} instance
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * This bean method just creates a MethodValidationPostProcessor instance and
	 * returns it.
	 * 
	 * @return {@link MethodValidationPostProcessor} instance
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
