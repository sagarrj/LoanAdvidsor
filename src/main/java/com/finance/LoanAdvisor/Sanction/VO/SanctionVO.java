package com.finance.LoanAdvisor.Sanction.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SanctionVO {
    private double LoanAmount;
    private double roi;
    private String loanType;

}
