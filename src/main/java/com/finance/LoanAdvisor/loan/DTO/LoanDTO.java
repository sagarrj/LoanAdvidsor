package com.finance.LoanAdvisor.loan.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private String loanType;

    



}
