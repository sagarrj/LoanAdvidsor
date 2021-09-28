package com.finance.LoanAdvisor.loan.VO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoanVO {
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private String loanType;
}
