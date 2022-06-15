package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}