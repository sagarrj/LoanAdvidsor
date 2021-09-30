package com.finance.LoanAdvisor.customer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Integer income;
    private Integer creditScore;


}
