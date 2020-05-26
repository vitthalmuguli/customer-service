package com.eatza.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatza.customer.model.Customer;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	/**
	 * Get customer data by using userId
	 * 
	 * @param id
	 * @return {@link Customer}
	 */
	public Customer findByIdAndIsActiveIsTrue(Integer userId);

	/**
	 * Get customer data by using userName
	 * 
	 * @param username
	 * @return {@link Customer}
	 */
	public Customer findByUsernameAndIsActiveIsTrue(String userName);
}
