package com.finance.loanadvisor.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class contain required parameters for RegisterResponse and required
 * constructors
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private Integer tenure;
    private Double emi;
}
