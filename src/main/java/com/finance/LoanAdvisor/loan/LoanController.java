package com.finance.LoanAdvisor.loan;

import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.loan.VO.LoanVO;
import com.finance.LoanAdvisor.loan.VO.RegisterRequest;
import com.finance.LoanAdvisor.loan.VO.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

	Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanService loanService ;

    @GetMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest
	){

        RegisterResponse registerResponse = loanService.registerCustomerForLoan(registerRequest);
        return  new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.OK);
    }


	@GetMapping(value = "/getLoan/{id}")
	public ResponseEntity<Loan> getLoanById(@PathVariable int id) {
	 Loan loan=loanService.getLoan(id).get();
	 return  new ResponseEntity<Loan>(loan, HttpStatus.OK);
    }


	@GetMapping(value = "/getAllLoan")
	public List<LoanVO> getAllLoans() {
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