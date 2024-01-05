package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {
}