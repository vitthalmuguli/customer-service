package com.eatza.customer.dto;

import lombok.Data;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@Data
public class UserDto {
	private String username;
	private String password;
	private String grant_type;
}
