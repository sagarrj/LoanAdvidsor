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
    
	public LoanVO(Integer loanId, String loanDesc, Double rOI, String loanType) {
		super();
		this.loanId = loanId;
		this.loanDesc = loanDesc;
		ROI = rOI;
		this.loanType = loanType;
	}
    
    
    
}
