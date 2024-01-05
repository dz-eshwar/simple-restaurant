package com.britr.simpleRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse implements Serializable {

    private String orderId;
    private List<DeleteResponseDetails> deleteResponseDetailsList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteResponseDetails {
        private Integer menuId;
        private Integer tableId;
        private String deletionStatus;
        private String message;
    }
}
