package com.finance.LoanAdvisor.loan.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoanDTO {
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private String loanType;
    
	public LoanDTO(Integer loanId, String loanDesc, Double rOI, String loanType) {
		super();
		this.loanId = loanId;
		this.loanDesc = loanDesc;
		ROI = rOI;
		this.loanType = loanType;
	}
    
    
    
}
