package com.squad.pizzahut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.squad.pizzahut.dto.FoodMenuResponse;
import com.squad.pizzahut.dto.FoodResponse;
import com.squad.pizzahut.dto.FoodResponseDto;
import com.squad.pizzahut.entity.Category;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserFoodOrder;
import com.squad.pizzahut.entity.UserOrder;
import com.squad.pizzahut.exception.UserNotFoundException;
import com.squad.pizzahut.repository.CategoryRepository;
import com.squad.pizzahut.repository.FoodRepository;
import com.squad.pizzahut.repository.UserFoodOrderRepository;
import com.squad.pizzahut.repository.UserOrderRepository;
import com.squad.pizzahut.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserOrderRepository userOrderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserFoodOrderRepository userFoodOrderRepository;
	
	/**
	 *
	 */
	public FoodResponseDto getFoodMenu(Long userId) throws UserNotFoundException {
		log.info("Entering into getFoodMenu() method of UserServiceImpl");	
		List<Category> categoryList=categoryRepository.findAll();	
		List<Food> foodList=foodRepository.findAll();
		List<FoodMenuResponse> foodMenuResponseList= new ArrayList<>();
		List<FoodResponse> preferenceList= new ArrayList<>();
		if(foodList.isEmpty()) {
			log.debug("Food List is Empty");
			return new FoodResponseDto();
		}
		//logic to get all the menu list
		categoryList.forEach(category->{
			List<FoodResponse> foodCategoryList=foodList.stream().filter(foodCategory->foodCategory.getCategory().equals(category)).map(foodCategory->convertFoodToFoodResponse(foodCategory)).collect(Collectors.toList());
			FoodMenuResponse foodMenuResponse= new FoodMenuResponse();
			foodMenuResponse.setCategoryName(category.getCategoryName());
			foodMenuResponse.setFoodList(foodCategoryList);
			foodMenuResponseList.add(foodMenuResponse);
		}		
		);
		
		//logic to get the user preference list
		Optional<User> userResponse=userRepository.findByUserId(userId);
		if(!userResponse.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		List<UserOrder> userOrderList=userOrderRepository.findByUser(userResponse.get());
		List<UserFoodOrder> userFoodOrderList=userFoodOrderRepository.findAll();

		Map<Food,Integer> map = new HashMap<>();
		userFoodOrderList.forEach(index->{
		if(map.get(index.getFood()) != null) {
			map.put(index.getFood(), map.get(index.getFood())+1);
		}
		else {
			map.put(index.getFood(), 1);
		}
		}
		);
			
		FoodResponse foodResponse= new FoodResponse();
		Set<Entry<Food, Integer>> foodSet=map.entrySet();
		for(Entry<Food, Integer> index:foodSet) {		
				BeanUtils.copyProperties(index.getKey(), foodResponse);
				preferenceList.add(foodResponse);
		}
		FoodResponseDto foodResponseDto= new FoodResponseDto();
		foodResponseDto.setAllMenuList(foodMenuResponseList);
		foodResponseDto.setPreferenceList(preferenceList);
		return foodResponseDto;
		
	}
	
	private FoodResponse convertFoodToFoodResponse(Food foodCategory) {
		FoodResponse foodResponse= new FoodResponse();
		BeanUtils.copyProperties(foodCategory, foodResponse);
		return foodResponse;	
	}
	
	
}
