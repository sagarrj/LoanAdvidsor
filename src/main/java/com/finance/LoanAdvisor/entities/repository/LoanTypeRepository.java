package com.finance.LoanAdvisor.entities.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.LoanAdvisor.entities.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Integer>{

}
