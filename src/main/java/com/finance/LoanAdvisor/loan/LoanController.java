package com.finance.LoanAdvisor.loan;

import java.util.List;
import java.util.Optional;

import com.finance.LoanAdvisor.entities.Sanction;


import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

	Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanService loanService ;

    @GetMapping("/register/{cust_id}/{sanction_id}")
    public ResponseEntity<Sanction> register(@PathVariable(name = "cust_id") Integer customerId,
                                             @PathVariable(name = "sanction_id") Integer sanctionId
    ){

//        Sanction sanction = loanService.registerCustomerForLoan(customerId,sanctionId);
//        return  new ResponseEntity<Sanction>(sanction, HttpStatus.OK);
        return  new ResponseEntity<Sanction>(new Sanction(), HttpStatus.OK);
    }

	
	@GetMapping(value = "/getLoan/{id}")
	public ResponseEntity<Loan> getLoanById(@PathVariable int id) {
	 Loan loan=loanService.getLoan(id).get();
	 return  new ResponseEntity<Loan>(loan, HttpStatus.OK);

	}
	
	@GetMapping(value = "/getAllLoan")
	public List<Loan> getAllLoans() {
		return loanService.getAllLoan();

	}
	
//	@PostMapping("/add")
//	public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) throws DataNotFoundException{
//		logger.info("LoanType returned from controller");
//		Optional<Loan> loanInfo =loanService.addLoan(loan);
//		return new ResponseEntity<Loan>(loanInfo.get(),HttpStatus.CREATED);
//
//	}
//	

	
}