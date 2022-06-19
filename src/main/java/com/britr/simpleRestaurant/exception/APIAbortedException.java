package com.britr.simpleRestaurant.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class APIAbortedException extends ApiException{
    public APIAbortedException(HttpStatus httpStatus, String errorMessage, String errorCode) {
        super(httpStatus, errorMessage, errorCode);
    }

    public APIAbortedException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
