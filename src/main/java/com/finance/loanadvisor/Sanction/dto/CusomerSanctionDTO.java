package com.finance.loanadvisor.Sanction.dto;

import com.finance.loanadvisor.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CusomerSanctionDTO {

    private String firstName;
    private String lastName;
    private String loanType;
    private String loanRequirement;
    private Double roi;
}
