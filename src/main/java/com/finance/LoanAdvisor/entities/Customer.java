package com.finance.LoanAdvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String email;
    private Integer age;
    private String phoneNo;
    private Integer income;
    private String aadharNo;
    private String panNo;
    private Integer creditScore;
    private Integer initialAmount;
    private Integer loanRequirement;
    
    private char status = 'A';
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
    private Integer updatedBy;

}
