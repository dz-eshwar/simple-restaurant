package com.britr.simpleRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest implements Serializable {

    private Integer menuId;
    private Integer quantity;
    private Integer tableId;
    private Integer userId;
    private Integer deviceId;
    private Integer price;

}
