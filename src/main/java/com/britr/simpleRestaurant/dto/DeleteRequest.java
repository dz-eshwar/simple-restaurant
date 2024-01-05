package com.britr.simpleRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRequest implements Serializable {
    private String orderId;
    private List<DeleteRequestDetails> deleteRequestDetailsList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteRequestDetails implements Serializable{
        private Integer menuId;
        private Integer tableId;
        private Integer quantity;
    }
}
