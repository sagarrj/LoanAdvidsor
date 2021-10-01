package com.finance.loanadvisor.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
/**
 * This entity class includes declaration of parameters of Sanction class, no
 * arguments and required arguments constructor, getter and setter of parameters. Mapped
 * {@link Customer} and {@link Loan} table.
 */
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
