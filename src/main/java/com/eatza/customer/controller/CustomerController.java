package com.eatza.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.exception.CustomerNotFoundException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.service.CustomerService;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@RestController
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired CustomerService customerService;
	
	@Autowired KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "testcustomer";

	@PostMapping("/customer")
	public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerDto customerDto) {
		Customer customer = customerService.registerCustomer(customerDto);
		//kafkaTemplate.send(TOPIC, customer.getUsername() + " customer is created");
		sendMessage(customer.getUsername() + " customer is created");
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}
	
	/**
	 * Send message for call back
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.debug(
						"Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.debug("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
	
	@PutMapping("/customer/{customerId}")
	public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto customerDto,
			@PathVariable Integer customerId) {
		boolean result = customerService.updateCustomer(customerDto, customerId);
		if (result) {
			return ResponseEntity.status(HttpStatus.OK).body("Customer data updated Successfully");
		} else {
			throw new CustomerNotFoundException("No records found with respective id");
		}
	}
	
	@PutMapping("/customer/deactivateCustomer/{customerId}")
	public ResponseEntity<String> deactivateCustomer(@PathVariable Integer customerId) {
		boolean result = customerService.deactivateCustomer(customerId);
		if (result) {
			return ResponseEntity.status(HttpStatus.OK).body("Customer deactivated Successfully");
		} else {
			throw new CustomerNotFoundException("No records found with respective id");
		}
	}
	
	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable Integer customerId) {
		System.out.println(">>>>>>>>>>>>>>> I'm inside getCustomer >>>>>>>>>>>>");
		Customer customer = customerService.getCustomer(customerId);
		return customer;
	}
}
