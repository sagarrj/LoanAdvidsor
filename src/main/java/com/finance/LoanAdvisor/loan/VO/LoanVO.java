package com.finance.LoanAdvisor.loan.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanVO {
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private String loanType;
}
