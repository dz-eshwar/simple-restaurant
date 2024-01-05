package com.britr.simpleRestaurant.controller;

import com.britr.simpleRestaurant.dto.DeleteRequest;
import com.britr.simpleRestaurant.dto.OrderRequest;
import com.britr.simpleRestaurant.dto.OrderResponse;
import com.britr.simpleRestaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllTables")
    public ResponseEntity getAllTables(){
       return ResponseEntity.ok(orderService.getTablesList());
    }

    @GetMapping("/getAllMenu")
    public ResponseEntity getAllMenu(){
        return new ResponseEntity<>(orderService.getAllMenu(), HttpStatus.OK);
    }

    @PostMapping("/addOrder")
    public ResponseEntity addOrder(@RequestBody List<OrderRequest> requestList,
                                   @RequestAttribute("$") Integer id,
                                   @RequestHeader("deviceId") Integer deviceID){
        OrderResponse orderResponse = orderService.createOrder(requestList,id,deviceID);
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }

    @GetMapping("/getOrderByOrderId/{orderId}")
    public ResponseEntity getOrderByOrderId(@PathVariable("orderId") String orderId){
        return new ResponseEntity<>(orderService.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/getOrderByTableId/{tableId}")
    public ResponseEntity getOrderByTableId(@PathVariable("tableId") Integer tableId){
        return new ResponseEntity<>(orderService.getOrderByTableId(tableId), HttpStatus.OK);
    }



    @PostMapping("/deleteOrder")
    public ResponseEntity deleteOrder(@RequestBody DeleteRequest request){
        System.out.println(request);
        return new ResponseEntity<>(orderService.deleteOrder(request), HttpStatus.OK);
    }

}
