package com.finance.LoanAdvisor.entities.repository;
import com.finance.LoanAdvisor.entities.Customer;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author priypawa This interface extends {@link JpaRepository} which provides
 *         JPA functionalities for the entity class {@link Customer} that
 *         is being managed.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	public Customer findByEmail(String email);

	public List<Customer> findAllByStatus(char c);
}
