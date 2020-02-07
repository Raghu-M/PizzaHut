package com.squad.pizzahut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad.pizzahut.entity.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long>{

}
