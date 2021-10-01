package com.finance.loanadvisor.customer.dto;

import com.finance.loanadvisor.entities.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author priypawa This class contains required parameters of {@link Customer}
 *         class and arguments constructor
 *
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Integer customerId;
	private String firstName;
	private String lastName;
	private String email;
	private Integer age;
	private Integer income;
	private Integer creditScore;

}
