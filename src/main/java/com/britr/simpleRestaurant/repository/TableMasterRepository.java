package com.britr.simpleRestaurant.repository;

import com.britr.simpleRestaurant.model.TableMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableMasterRepository extends JpaRepository<TableMaster, Integer> {

    public List<TableMaster> findAll();
}