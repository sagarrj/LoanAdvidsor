package com.finance.loanadvisor.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contain required parameters for RegisterRequest and required
 * constructors
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private Integer customerId;
	private Integer sanctionId;
	private Integer preferredTenure;

}
