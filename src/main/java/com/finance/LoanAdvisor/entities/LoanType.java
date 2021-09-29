package com.finance.LoanAdvisor.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanType {
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "LOAN_TYPE_ID")
	    private Integer loanTypeId;
	    @Column(name = "LOAN_DESCRIPTION")
	    private String loanDescription;
	    @Column(name = "STATUS")
	    private char status = 'A';
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CREATE_DTTMS")
	    private Date createDttm;
	    @Column(name = "CREATED_BY")
	    private Integer createdBy;
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "UPDATE_DTTM")
	    private Date updateDttm;
	    @Column(name = "UPDATE_BY")
	    private Integer updatedBy;
	
	   

}
