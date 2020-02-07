package com.squad.pizzahut.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.OrderSummary;
import com.squad.pizzahut.dto.OrderedFood;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.dto.VendorOrderResponseDto;
import com.squad.pizzahut.entity.Food;
import com.squad.pizzahut.entity.User;
import com.squad.pizzahut.entity.UserFoodOrder;
import com.squad.pizzahut.entity.UserOrder;
import com.squad.pizzahut.entity.VendorFoodCategory;
import com.squad.pizzahut.exception.NotFoundException;
import com.squad.pizzahut.repository.CategoryRepository;
import com.squad.pizzahut.repository.UserFoodOrderRepository;
import com.squad.pizzahut.repository.UserOrderRepository;
import com.squad.pizzahut.repository.UserRepository;
import com.squad.pizzahut.repository.VendorFoodCategoryRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author User1
 *
 */

@Service
@Slf4j
public class UserOrderServiceImpl implements UserOrderService {

	@Autowired
	UserOrderRepository userOrderRepository;

	@Autowired
	UserFoodOrderRepository userFoodOrderRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VendorFoodCategoryRepository vendorFoodCategoryRepository;

	/**
	 * 
	 * getOrders method fetch the user order based on the user id.
	 * 
	 *  @param Long type userId is taken as parameter to get that user order history.
	 *  
	 *  @return list of user order history
	 *  
	 *  @throws NotFoundException.class
	 * 
	 */

	@Override
	public UserOrderResponseDto getUserOrders(Long userId) throws NotFoundException {
		log.info("UserOrderServiceImpl getOrders ----> fetching user order");
		if (!userRepository.findById(userId).isPresent()) {
			log.error("UserOrderServiceImpl getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_NOT_FOUND);
		}

		User user = new User();
		user.setUserId(userId);
		List<UserOrder> userOrders = userOrderRepository.findByUserOrderByUserOrderIdDesc(user);
		UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto();
		List<OrderSummary> orderSummarys = new ArrayList<>();
		userOrders.forEach(userOrder -> {
			List<UserFoodOrder> userFoodOrders  = userFoodOrderRepository.findByUserOrder(userOrder);
			OrderSummary orderSummary = new OrderSummary();
			List<OrderedFood> orderedFoods = new ArrayList<>();
			userFoodOrders.forEach(userFoodOrder -> {
				OrderedFood orderedFood = new OrderedFood();
				orderedFood.setFoodName(userFoodOrder.getFood().getFoodName());
				orderedFood.setQuantity(userFoodOrder.getQuantity());
				orderedFoods.add(orderedFood);
			});
			orderSummary.setOrderDate(userOrder.getOrderDate());
			orderSummary.setTotalAmount(userOrder.getTotalAmount());
			orderSummary.setUserOrderId(userOrder.getUserOrderId());
			orderSummary.setOrderedFoods(orderedFoods);
			orderSummary.setPaymentType(userOrder.getPaymentType());
			orderSummary.setOrderStatus(userOrder.getOrderStatus());
			orderSummarys.add(orderSummary);
		});
		
		userOrderResponseDto.setOrderSummarys(orderSummarys);
		return userOrderResponseDto;
	}

	@Override
	public VendorOrderResponseDto getVendorOrders(Long vendorId) throws NotFoundException {
		log.info("UserOrderServiceImpl getVendorOrders ----> fetching vendor orders");
		if (!userRepository.findById(vendorId).isPresent()) {
			log.error("UserOrderServiceImpl getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_NOT_FOUND);
		}
		User user = new User();
		user.setUserId(vendorId);
		List<VendorFoodCategory> vendorFoodCategorys = vendorFoodCategoryRepository.findByUser(user);
		vendorFoodCategorys.forEach(vendorFoodCategory -> {
			Food food = new Food();
			food.setFoodId(vendorFoodCategory.getFood().getFoodId());
			List<UserFoodOrder> userFoodOrder = userFoodOrderRepository.findByFood(food);
		});
		
		return null;
	}

}
