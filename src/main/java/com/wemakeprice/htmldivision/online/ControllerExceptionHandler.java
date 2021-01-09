package com.wemakeprice.htmldivision.online;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        logger.error("BindException",ex);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Map<String, String>> handleUnknownHostException(UnknownHostException ex){
        Map<String, String> errors = new HashMap<>();

        logger.error("UnknownHostException",ex);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException ex){

        logger.error("NullPointerException",ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex){

        logger.error("Exception",ex);
        return ResponseEntity.badRequest().build();
    }
}
