package com.finance.loanadvisorr.entities;


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
public class Borrower {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowerId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "SANCTION_ID")
    private Sanction sanction;
    private Integer tenure;
    private Double emi;
    private char status = 'A';
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
    private Integer updatedBy;
}
