package com.finance.loanadvisorr.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private String loanType;

  

}
