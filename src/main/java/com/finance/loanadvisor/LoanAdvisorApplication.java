package com.finance.loanadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class LoanAdvisorApplication {
	/**
	 * This method create {@link ApplicationContext} instance. Start up
	 * {@link SpringBootApplication}
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoanAdvisorApplication.class, args);
	}

}



