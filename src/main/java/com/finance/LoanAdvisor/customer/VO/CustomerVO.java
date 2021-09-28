package com.finance.LoanAdvisor.customer.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerVO {

    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Integer income;
    private Integer creditScore;


}
