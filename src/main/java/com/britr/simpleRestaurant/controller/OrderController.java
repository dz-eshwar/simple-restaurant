package com.britr.simpleRestaurant.controller;

import com.britr.simpleRestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllTables")
    public ResponseEntity getAllTables(@RequestAttribute("$") Integer id){
       System.out.println("entered in order with id::"+id);
       return ResponseEntity.ok(orderService.getTablesList());
    }


}
