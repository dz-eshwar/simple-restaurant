package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {
}