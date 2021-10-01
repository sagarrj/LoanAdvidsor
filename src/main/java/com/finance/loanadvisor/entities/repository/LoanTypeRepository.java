package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, Integer>{

}
