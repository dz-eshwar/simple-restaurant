package com.britr.simpleRestaurant.controller.controllerAdvice;

import com.britr.simpleRestaurant.exception.APIAbortedException;
import com.britr.simpleRestaurant.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String EXCEPTION_DETAILS = "Exception Details::";

    @ExceptionHandler(APIAbortedException.class)
    public ResponseEntity handleApiException(ApiException exception){
        log.error(EXCEPTION_DETAILS,exception);
        Map<String,String> messageMap = new HashMap<>();
        if(exception.getHttpStatus().is2xxSuccessful()){
            messageMap.put("code", exception.getErrorCode());
            messageMap.put("message",exception.getErrorMessage());
            return new ResponseEntity(messageMap, HttpStatus.OK);
        }else if(exception.getHttpStatus().is4xxClientError()){
            messageMap.put("code", exception.getErrorCode());
            messageMap.put("message",exception.getErrorMessage());
            return new ResponseEntity(messageMap, HttpStatus.CONFLICT);
        }
        else{
            messageMap.put("code", "ERR500");
            messageMap.put("message",exception.getMessage());
            return new ResponseEntity(messageMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
