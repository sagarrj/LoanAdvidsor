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
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sanctionId;
/*
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "loanId")
    private Loan loan;*/

    private char status = 'A';
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
    private Integer updatedBy;

}
