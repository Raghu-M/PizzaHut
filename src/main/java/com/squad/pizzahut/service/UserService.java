package com.squad.pizzahut.service;

import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.exception.UserNotFoundException;

public interface UserService {
	public FoodResponseDto getFoodMenu(Long userId) throws UserNotFoundException;
}
