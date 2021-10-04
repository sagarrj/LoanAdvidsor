package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author priypawa This interface extends {@link JpaRepository} which provides
 *         JPA functionalities for the entity class {@link Customer} and contain
 *         custom methods such as findAllByStatus and findByEmail
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByEmail(String email);

	public List<Customer> findAllByStatus(char c);
}
