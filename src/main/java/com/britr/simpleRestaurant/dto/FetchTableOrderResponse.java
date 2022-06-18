package com.britr.simpleRestaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchTableOrderResponse implements Serializable {

    private Integer tableId;
    private List<FetchOrderResponse> orderResponseList;
}
