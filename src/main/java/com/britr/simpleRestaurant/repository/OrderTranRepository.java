package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.OrderTrans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTranRepository extends JpaRepository<OrderTrans, String> {
}