package com.squad.pizzahut.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/{userId}/foods")
	public ResponseEntity<FoodResponseDto> getFoodMenu(@Valid @PathVariable Long userId){
		log.info("Entering into getFoodMenu of UserController");
		FoodResponseDto foodResponseDto= userService.getFoodMenu(userId);
		foodResponseDto.setStatusCode(200);
		foodResponseDto.setStatusMessage("Success");
		return new ResponseEntity<FoodResponseDto>(foodResponseDto, HttpStatus.OK);
	}
}
