package com.squad.pizzahut.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	@NotNull(message = "employeeId is required")
	private String email;
	@NotNull(message = "password is required")
	private String password;

}
