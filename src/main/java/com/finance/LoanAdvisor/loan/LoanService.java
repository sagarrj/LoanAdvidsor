package com.finance.LoanAdvisor.loan;

import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
	
	
	  private final LoanRepository loanRepository;

    private final SanctionRepository sanctionRepository;
    Logger logger = LoggerFactory.getLogger(LoanController.class);

    public Sanction registerCustomerForLoan(Integer customerId, Integer sanctionId) {

        Optional<Sanction> optionalSanction = sanctionRepository.findById(sanctionId);
        return optionalSanction.isPresent() ? optionalSanction.get() : null;

    }

	public Optional<Loan> getLoan(int id) throws DataNotFoundException{
		
		Optional<Loan> loan=loanRepository.findById(id);
		return loan;
	}
	
	public List<Loan> getAllLoan() throws DataNotFoundException{
		logger.info("List of All Users Retreived Sucessfully "+loanRepository.findAll());
		return loanRepository.findAll();
	}
	
//	public Optional<Loan> addLoan(Loan loan) throws DataNotFoundException{
//		if((loan.getStatus()!='A')) {
//			throw new DataNotFoundException("Loan is already created");
//		}
//		Loan loanInfo =loanRepository.save(loan);
//		logger.info("Loan Created");
//		return Optional.of(loanInfo);
//	}
//	
	


}
