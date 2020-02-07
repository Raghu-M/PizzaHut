package com.squad.pizzahut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.UserFoodOrder;

@Repository
public interface UserFoodOrderRepository extends JpaRepository<UserFoodOrder, Long>{

}
