package com.eatza.customer.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.eatza.customer.dto.CustomerDto;
import com.eatza.customer.exception.CustomerNotFoundException;
import com.eatza.customer.model.Customer;
import com.eatza.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@MockBean CustomerService customerService;
	
	@MockBean KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired MockMvc mockMvc;
	
	@Autowired private ObjectMapper objectMapper;
	
	Customer customer;
	
	CustomerDto customerDto;

	@Before
	public void createStubs() {
		customer = new Customer(1, "Vitthal", "Baburao", "vitthal", "vitthal");
		customerDto = new CustomerDto(customer);
	}

	@After
	public void deleteStubs() {
		customer = null;
		customerDto = null;
	}

	@Test
	public void getCustomer_Exists() throws Exception {
		Mockito.when(customerService.getCustomer(Mockito.anyInt())).thenReturn(customer);
		RequestBuilder request = MockMvcRequestBuilders.get("/customer/1").accept(MediaType.ALL);
		mockMvc.perform(request).andExpect(status().is(200)).andReturn();
	}

	@Test
	public void getCustomer_NotFound() throws Exception {
		Mockito.when(customerService.getCustomer(Mockito.anyInt())).thenThrow(new CustomerNotFoundException());
		RequestBuilder request = MockMvcRequestBuilders.get("/customer/100").accept(MediaType.ALL);
		mockMvc.perform(request).andExpect(status().is(404)).andReturn();
	}

	@Test
	public void registerCustomer() throws Exception {
		Mockito.when(customerService.registerCustomer(Mockito.any(CustomerDto.class))).thenReturn(customer);
		RequestBuilder request = MockMvcRequestBuilders.post("/customer").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((customerDto))).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is(201)).andReturn();
		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void deactiveCustomer_found() throws Exception {
		Mockito.when(customerService.deactivateCustomer(Mockito.anyInt())).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.put("/customer/deactivateCustomer/1");
		mockMvc.perform(request).andExpect(status().is(200)).andReturn();
		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deactiveCustomer_notFound() throws Exception {
		Mockito.when(customerService.deactivateCustomer(Mockito.anyInt())).thenReturn(false);
		RequestBuilder request = MockMvcRequestBuilders.put("/customer/deactivateCustomer/100");
		mockMvc.perform(request).andExpect(status().is(404)).andReturn();
		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void updateCustomer_Found() throws Exception {
		Mockito.when(customerService.updateCustomer(Mockito.any(CustomerDto.class), Mockito.anyInt())).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.put("/customer/1").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((customerDto))).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is(200)).andReturn();
		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateCustomer_NotFound() throws Exception {
		Mockito.when(customerService.updateCustomer(Mockito.any(CustomerDto.class), Mockito.anyInt()))
				.thenReturn(false);
		RequestBuilder request = MockMvcRequestBuilders.put("/customer/100").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString((customerDto))).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is(404)).andReturn();
		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
}
