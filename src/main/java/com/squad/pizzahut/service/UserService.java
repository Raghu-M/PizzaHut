package com.squad.pizzahut.service;

import com.squad.pizzahut.dto.FoodResponseDto;

public interface UserService {
	public FoodResponseDto getFoodMenu(Long userId);
}
