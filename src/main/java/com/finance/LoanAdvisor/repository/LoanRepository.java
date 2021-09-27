package com.finance.LoanAdvisor.repository;

import com.finance.LoanAdvisor.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
}