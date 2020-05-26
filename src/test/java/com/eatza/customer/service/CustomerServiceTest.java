package com.eatza.customer.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.exception.CustomerNotFoundException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.repository.CustomerRepository;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

	@InjectMocks CustomerServiceImpl customerService;
	
	@Mock CustomerRepository customerRepository;

	Customer customer;

	CustomerDto customerDto;

	@Before
	public void createStubs() {
		customer = new Customer(1, "Vitthal", "Baburao", "vitthal", "vitthal");
		customerDto = new CustomerDto(customer);
	}

	@Test
	public void findById() {
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));
		Customer customer = customerService.getCustomer(1);
		assertEquals("vitthal", customer.getUsername());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void findById_NotFound() {
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

		customerService.getCustomer(100);
	}

	@Test
	public void registerCustomer() {
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		Customer customerTest = customerService.registerCustomer(customerDto);
		assertEquals("vitthal", customerTest.getUsername());
	}

	@Test
	public void updateCustomer_found() {
		when(customerRepository.findByIdAndIsActiveIsTrue(Mockito.anyInt())).thenReturn(customer);
		boolean updatedStatus = customerService.updateCustomer(customerDto, 1);
		assertEquals(true, updatedStatus);
	}

	@Test
	public void updateCustomer_NotFound() {
		when(customerRepository.findByIdAndIsActiveIsTrue(Mockito.anyInt())).thenReturn(null);
		boolean updatedStatus = customerService.updateCustomer(customerDto, 200);
		assertEquals(false, updatedStatus);
	}

	@Test
	public void deactivateCustomer_found() {
		when(customerRepository.findByIdAndIsActiveIsTrue(Mockito.anyInt())).thenReturn(customer);
		boolean updatedStatus = customerService.deactivateCustomer(1);
		assertEquals(true, updatedStatus);
	}

	@Test
	public void deactivateCustomer_NotFound() {
		when(customerRepository.findByIdAndIsActiveIsTrue(Mockito.anyInt())).thenReturn(null);
		boolean updatedStatus = customerService.deactivateCustomer(200);
		assertEquals(false, updatedStatus);
	}
}