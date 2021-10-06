package com.finance.loanadvisor.Sanction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerLoanSanctionDTO {
    private Integer age;
    private Integer income;
    private Integer creditScore;
    private Double roi;
    private String loanDesc;
    private String gender;
    private Integer loanRequirement;
}
