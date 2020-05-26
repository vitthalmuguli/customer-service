package com.eatza.customer.service;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.model.Customer;

/**
 * 
 * @author Vitthal Baburao
 *
 */
public interface CustomerService {

	/**
	 * Get register customer
	 * 
	 * @param customerDto
	 * @return {@link Customer}
	 */
	public Customer registerCustomer(CustomerDto customerDto);

	/**
	 * De-activate customerId
	 * 
	 * @param customerId
	 * @return {@link true/false}
	 */
	public boolean deactivateCustomer(Integer customerId);

	/**
	 * Update customer information
	 * 
	 * @param customerDto
	 * @param customerId
	 * @return {@link true/false}
	 */
	public boolean updateCustomer(CustomerDto customerDto, Integer customerId);

	/**
	 * Get customer data by using customerId
	 * 
	 * @param customerId
	 * @return {@link Customer}
	 */
	public Customer getCustomer(Integer customerId);
}
