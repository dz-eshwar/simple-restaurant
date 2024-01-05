package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.MenuMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuMasterRepository extends JpaRepository<MenuMaster, Integer> {
}