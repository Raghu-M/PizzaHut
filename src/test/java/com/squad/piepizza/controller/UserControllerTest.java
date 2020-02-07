package com.squad.piepizza.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.pizzahut.controller.UserController;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.service.UserOrderService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserOrderService userOrderService;
	
	@Test(expected = NotFoundException.class)
	public void testGetUserOrderUserIdMissing() throws NotFoundException {
		
		userController.getOrders(null);
	}
	
	@Test
	public void testGetUserOrderSuccess() throws NotFoundException {
		Mockito.when(userOrderService.getUserOrders(1L)).thenReturn(new UserOrderResponseDto());
		Integer actual = userController.getOrders(1L).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}

}
