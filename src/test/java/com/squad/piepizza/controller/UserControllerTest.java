package com.squad.piepizza.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squad.pizzahut.controller.UserController;
import com.squad.pizzahut.dto.LoginRequestDto;
import com.squad.pizzahut.dto.LoginResponseDto;
import com.squad.pizzahut.dto.OrderRequestDto;
import com.squad.pizzahut.dto.OrderResponseDto;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.exception.FoodNotFoundException;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	User user = new User();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	OrderResponseDto orderResponseDto = new OrderResponseDto();

	@Before
	public void init() {
		loginRequestDto.setMobile(123456789L);
		loginRequestDto.setPassword("sri");
		user.setUserId(1L);

	}

	@Test
	public void testAuthenticateUser() throws UserNotFoundException {
		Mockito.when(userService.authenticateUser(loginRequestDto)).thenReturn(loginResponseDto);
		ResponseEntity<LoginResponseDto> actual = userController.authenticateUser(loginRequestDto);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void testPlaceOrder() throws UserNotFoundException, FoodNotFoundException {
		Mockito.when(userService.placeOrder(orderRequestDto, 1L)).thenReturn(orderResponseDto);
		ResponseEntity<OrderResponseDto> response = userController.placeOrder(orderRequestDto, 1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
