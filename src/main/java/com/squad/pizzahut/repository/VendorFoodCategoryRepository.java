package com.squad.pizzahut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.VendorFoodCategory;

@Repository
public interface VendorFoodCategoryRepository extends JpaRepository<VendorFoodCategory, Integer>{

}
