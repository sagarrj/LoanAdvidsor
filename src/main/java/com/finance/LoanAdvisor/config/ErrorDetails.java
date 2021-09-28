package com.finance.LoanAdvisor.config;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author priypawa This class includes declaration of parameters of
 *         ErrorDetails class,no argument constructors,
 *         getter and setter of parameters.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

	private String message;
	private LocalDate date;
	private String details;
}
