package com.britr.simpleRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse implements Serializable {
    private String orderId;
    private List<ResponseDetails> responseDetails;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
   public static class ResponseDetails{
       private Integer menuId;
       private String menuName;
       private Integer quantity;
       private Integer timeToPrep;
   }

}
