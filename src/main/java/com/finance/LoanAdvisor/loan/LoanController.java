package com.finance.LoanAdvisor.loan;


import com.finance.LoanAdvisor.entities.Sanction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/register/{cust_id}/{sanction_id}")
    public ResponseEntity<Sanction> register(@PathVariable(name = "cust_id") Integer customerId,
                                             @PathVariable(name = "sanction_id") Integer sanctionId
    ){

//        Sanction sanction = loanService.registerCustomerForLoan(customerId,sanctionId);
//        return  new ResponseEntity<Sanction>(sanction, HttpStatus.OK);
        return  new ResponseEntity<Sanction>(new Sanction(), HttpStatus.OK);
    }


    @GetMapping("/sample")
    public String sample(){
        return "Sample";
    }
}
