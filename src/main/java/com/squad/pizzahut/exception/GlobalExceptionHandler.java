package com.squad.pizzahut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.ErrorDto;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> employeeNotFoundException(UserNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.USER_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

	@ExceptionHandler(FoodNotFoundException.class)
	public ResponseEntity<ErrorDto> foodNotFoundException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(Constant.FOOD_NOT_FOUND);
		errorDto.setStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

}