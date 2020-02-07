package com.squad.pizzahut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.pizzahut.dto.FoodMenuResponse;
import com.squad.pizzahut.dto.FoodResponse;
import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.entity.Category;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.repository.CategoryRepository;
import com.squad.pizzahut.repository.FoodRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public FoodResponseDto getFoodMenu(Long userId) {
		log.info("Entering into getFoodMenu() method of UserServiceImpl");	
		List<Category> categoryList=categoryRepository.findAll();	
		List<Food> foodList=foodRepository.findAll();
		List<FoodMenuResponse> foodMenuResponseList= new ArrayList<>();
		if(foodList.isEmpty()) {
			log.debug("Food List is Empty");
			return new FoodResponseDto();
		}
		
		categoryList.forEach(category->{
			List<FoodResponse> foodCategoryList=foodList.stream().filter(foodCategory->foodCategory.getCategory().equals(category)).map(foodCategory->convertFoodToFoodResponse(foodCategory)).collect(Collectors.toList());
			FoodMenuResponse foodMenuResponse= new FoodMenuResponse();
			foodMenuResponse.setCategoryName(category.getCategoryName());
			foodMenuResponse.setFoodList(foodCategoryList);
			foodMenuResponseList.add(foodMenuResponse);
		}		
		);
		FoodResponseDto foodResponseDto= new FoodResponseDto();
		foodResponseDto.setAllMenuList(foodMenuResponseList);
		return foodResponseDto;
		
	}
	
	private FoodResponse convertFoodToFoodResponse(Food foodCategory) {
		FoodResponse foodResponse= new FoodResponse();
		BeanUtils.copyProperties(foodCategory, foodResponse);
		return foodResponse;	
	}
}
