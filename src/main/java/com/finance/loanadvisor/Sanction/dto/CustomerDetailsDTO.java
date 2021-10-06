package com.finance.loanadvisor.Sanction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDTO {

    private String firstName;
    private String lastName;
    private Integer age;
    private String loanType;
    private Double loanAmount;
    private Integer monthlyIncome;
}
