package com.finance.LoanAdvisor.entities.repository;


import com.finance.LoanAdvisor.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
