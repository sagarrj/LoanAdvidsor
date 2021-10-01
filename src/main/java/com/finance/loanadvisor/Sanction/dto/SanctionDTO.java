package com.finance.loanadvisor.Sanction.dto;

import com.finance.loanadvisor.entities.Sanction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This class contains required parameters of {@link Sanction} class and
 * arguments constructor
 *
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SanctionDTO {
	private double LoanAmount;
	private double roi;
	private String loanType;

}
