package com.finance.LoanAdvisor.loan.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest
{
    private Integer customerId;
    private Integer sanctionId;
    private Integer preferredTenure;

}
