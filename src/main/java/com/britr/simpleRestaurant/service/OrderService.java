package com.britr.simpleRestaurant.service;

import com.britr.simpleRestaurant.model.TableMaster;
import com.britr.simpleRestaurant.model.UserDetail;
import com.britr.simpleRestaurant.repository.TableMasterRepository;
import com.britr.simpleRestaurant.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private TableMasterRepository tableMasterRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    public List<TableMaster> getTablesList(){
        List<TableMaster> tableMasters = tableMasterRepository.findAll();

        return tableMasters;
    }
}
