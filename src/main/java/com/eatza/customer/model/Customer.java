package com.eatza.customer.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eatza.customer.dto.CustomerDto;

/**
 * 
 * @author Vitthal Baburao
 *
 */

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;
	private boolean isActive;
	private String username;
	private String password;

	/**
	 * Default constructor
	 */
	public Customer() {
		
	}
	
	public Customer(CustomerDto customerDto) {
		this.firstName = customerDto.getFirstName();
		this.middleName = customerDto.getMiddleName();
		this.lastName = customerDto.getLastName();
		this.createDateTime = LocalDateTime.now();
		this.updateDateTime = LocalDateTime.now();
		this.isActive = customerDto.isActive();
		this.username = customerDto.getUsername();
		this.password = customerDto.getPassword();
	}

	public Customer(Integer id, String firstName, String lastName, String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.createDateTime = LocalDateTime.now();
		this.updateDateTime = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
