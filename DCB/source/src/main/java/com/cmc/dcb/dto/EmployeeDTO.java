package com.cmc.dcb.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

//	@NotBlank
	private String firstName;
//	@NotBlank
	private String lastName;
//	@NotBlank
	private String job;
//	@Positive
//	@Min(value = 100)
	private double salary;
}
