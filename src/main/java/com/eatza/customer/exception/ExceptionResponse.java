package com.eatza.customer.exception;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Vitthal Baburao
 *
 */
@Data
@NoArgsConstructor
public class ExceptionResponse {
	private LocalDate date;
	private String message;
	private String details;
	
	public ExceptionResponse(LocalDate date, String message, String details) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
	}
}
