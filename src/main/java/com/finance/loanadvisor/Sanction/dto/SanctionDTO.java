package com.finance.loanadvisor.Sanction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SanctionDTO {
    private double LoanAmount;
    private double roi;
    private String loanType;

}
