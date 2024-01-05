package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

    UserDetail findByUsername(String userName);
}