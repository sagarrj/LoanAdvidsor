package com.finance.LoanAdvisor.entities.repository;

import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.loan.VO.LoanVO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	List<Loan> findAllByStatus(char c);
}
