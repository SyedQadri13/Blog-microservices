package com.microservice.comment.exception;


import com.microservice.comment.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException e,
            WebRequest wB

    ){
        ErrorDetails eD = new ErrorDetails(e.getMessage(), new Date(), wB.getDescription(true));
        return new ResponseEntity<>(eD, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
