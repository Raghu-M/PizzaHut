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

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.service.UserOrderService;
import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	UserOrderService userOrderService;

	@Autowired
	UserService userService;

	/**
	 * 
	 * getOrders method fetch the user order based on the user id.
	 * 
	 * @param Long type userId is taken as parameter to get that user order history.
	 * 
	 * @return list of user order history enclosed with http response;
	 * 
	 * @throws NotFoundException.class
	 * 
	 */
	@GetMapping("/{userId}/orders")
	public ResponseEntity<UserOrderResponseDto> getOrders(@PathVariable("userId") Long userId)
			throws NotFoundException {
		log.info("UserController getOrders ----> fetching user order");
		if (userId == null) {
			log.error("UserController getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_ID_MISSING);
		}

		UserOrderResponseDto userOrderResponseDto = userOrderService.getUserOrders(userId);
		userOrderResponseDto.setMessage(Constant.SUCCESS);
		userOrderResponseDto.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.ok().body(userOrderResponseDto);
	}

	@GetMapping("/{userId}/foods")
	public ResponseEntity<FoodResponseDto> getFoodMenu(@Valid @PathVariable Long userId) throws UserNotFoundException{
		log.info("Entering into getFoodMenu of UserController");
		FoodResponseDto foodResponseDto = userService.getFoodMenu(userId);
		foodResponseDto.setStatusCode(200);
		foodResponseDto.setStatusMessage("Success");
		return new ResponseEntity<>(foodResponseDto, HttpStatus.OK);
	}

}
