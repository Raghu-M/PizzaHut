package com.squad.pizzahut.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.pizzahut.constant.Constant;
import com.squad.pizzahut.dto.UserOrderResponseDto;
import com.squad.pizzahut.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/vendors")
@Slf4j
public class VendorController {
	
	
	/**
	 * 
	 * getOrders method fetch the vendor orders based on the vendorId.
	 * 
	 *  @param Long type vendorId is taken as parameter to get the vendor orders
	 *  
	 *  @return list of vendor orders enclosed with http response;
	 *  
	 *  @throws NotFoundException.class
	 * 
	 */
	@GetMapping("/{vendorId}/orders")
	public ResponseEntity<UserOrderResponseDto> getOrders(@PathVariable("vendorId") Long vendorId) throws NotFoundException {
		log.info("VendorController getOrders ----> fetching vendor orders");
		if (vendorId == null) {
			log.error("VendorController getOrders ----> NotFoundException occured");
			throw new NotFoundException(Constant.USER_ID_MISSING);
		}
		return null;
		
	}

}
