package com.finance.LoanAdvisor.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer loanId;
    private String loanDesc;
    private Double ROI;
    private char status = 'A';
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDttm;
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDttm;
  
    private Integer updatedBy;
    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOAN_TYPE_ID")
    private LoanType loanType;
    
    

   /* @OneToMany(mappedBy = "loan")
    private Set<Sanction> sanctionSet = new HashSet<>();*/
}
