package com.britr.simpleRestaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchOrderResponse implements Serializable {
    private String orderId;
    private List<FetchOrderDetails> orderDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FetchOrderDetails{
        private Integer tableId;
        private String orderId;
        private Integer menuId;
        private String itemName;
        private Integer quantity;
        private Integer prepTime;
        private Integer timeLeftForPrepComplete;

    }
}
