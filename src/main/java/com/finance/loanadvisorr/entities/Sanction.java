package com.finance.loanadvisorr.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sanctionId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "LOAN_ID")
    private Loan loan;
    private Double loanAmount;
    private Double ROI;
    private char status = 'A';
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
    private Integer updatedBy;

}
