package com.finance.loanadvisor.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author priypawa This entity class includes declaration of parameters of 
 *         Customer class, no arguments and required arguments constructors, getter
 *         and setter of parameters.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer customerId;
    
	@NotEmpty(message = "First name should not be empty")
	@Pattern(regexp = "[A-Z]{1}[a-z]*",message="First Name should start with capital")
	private String firstName;
    
	@NotEmpty(message = "Last name should not be empty")
	@Pattern(regexp = "[A-Z]{1}[a-z]*", message = "Last Name should start with capital")
    private String lastName;
    
	@NotEmpty(message = "City should not be empty")
	@Pattern(regexp = "[A-Z]{1}[a-z]*", message = "City Name should start with capital")
    private String city;
    
	@NotEmpty(message = "Gender should not be empty")
	@Pattern(regexp = "Male|Female|Other", message = "Invalid Gender")
    private String gender;
    
	@NotEmpty(message = "Email should not be empty")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email")
    private String email;
    
	@NotNull(message = "Age should not be empty")
	//@Pattern(regexp = "", message = "Invalid Age")
    private Integer age;
	
	@NotEmpty(message = "Phone Number should not be empty")
	@Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Invalid Phone number")
    private String phoneNo;
    
	@NotNull(message = "Income should not be empty")
    private Integer income;
    
	@NotEmpty(message = "Aadhar Number should not be empty")
	@Pattern(regexp = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$", message = "Invalid Aadhar number")
    private String aadharNo;
    
	@NotEmpty(message = "Pan Number should not be empty")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid Pan number")
    private String panNo;
    
    private Integer creditScore;
    
	@NotNull(message = "Initial Amount should not be empty")
    private Integer initialAmount;
    
	@NotNull(message = "Loan Requirement should not be empty")
	private Integer loanRequirement;
    
    
    private char status = 'A';   
    @Temporal(TemporalType.TIMESTAMP)   
    private Date createDttm;   
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)   
    private Date updateDttm;
    private Integer updatedBy;
	public Customer(Integer customerId,
			@NotEmpty(message = "First name should not be empty") @Pattern(regexp = "[A-Z]{1}[a-z]*", message = "First Name should start with capital") String firstName,
			@NotEmpty(message = "Last name should not be empty") @Pattern(regexp = "[A-Z]{1}[a-z]*", message = "Last Name should start with capital") String lastName,
			@NotEmpty(message = "City should not be empty") @Pattern(regexp = "[A-Z]{1}[a-z]*", message = "City Name should start with capital") String city,
			@NotEmpty(message = "Gender should not be empty") @Pattern(regexp = "Male|Female|Other", message = "Invalid Gender") String gender,
			@NotEmpty(message = "Email should not be empty") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid Email") String email,
			@NotNull(message = "Age should not be empty") Integer age,
			@NotEmpty(message = "Phone Number should not be empty") @Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Invalid Phone number") String phoneNo,
			@NotNull(message = "Income should not be empty") Integer income,
			@NotEmpty(message = "Aadhar Number should not be empty") @Pattern(regexp = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$", message = "Invalid Aadhar number") String aadharNo,
			@NotEmpty(message = "Pan Number should not be empty") @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid Pan number") String panNo,
			@NotNull(message = "Initial Amount should not be empty") Integer initialAmount,
			@NotNull(message = "Loan Requirement should not be empty") Integer loanRequirement) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.gender = gender;
		this.email = email;
		this.age = age;
		this.phoneNo = phoneNo;
		this.income = income;
		this.aadharNo = aadharNo;
		this.panNo = panNo;
		this.initialAmount = initialAmount;
		this.loanRequirement = loanRequirement;
	}
    
    

}
