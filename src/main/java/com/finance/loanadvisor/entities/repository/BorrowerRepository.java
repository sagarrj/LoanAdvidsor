package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Borrower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface extends {@link JpaRepository} which provides JPA
 * functionalities for the entity class {@link Borrower} and contain custom
 * method such as findByCustomer_customerIdAndSanction_sanctionId
 */
@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {

	List<Borrower> findByCustomer_customerIdAndSanction_sanctionId(Integer customerId, Integer sanctionId);
}
