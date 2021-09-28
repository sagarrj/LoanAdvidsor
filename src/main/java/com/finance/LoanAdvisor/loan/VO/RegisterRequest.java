package com.finance.LoanAdvisor.loan.VO;

import lombok.Data;

@Data
public class RegisterRequest
{
    private Integer customerId;
    private Integer sanctionId;
    private Integer preferredTenure;


}
