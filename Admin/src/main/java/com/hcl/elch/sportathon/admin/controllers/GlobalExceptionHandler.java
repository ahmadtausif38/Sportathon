package com.hcl.elch.sportathon.admin.controllers;


import com.hcl.elch.sportathon.admin.customException.DuplicateValueException;
import com.hcl.elch.sportathon.admin.customException.NullValueException;
import com.hcl.elch.sportathon.admin.customException.ValueNotPresentException;
import com.hcl.elch.sportathon.admin.customException.WrongValueException;
import com.hcl.elch.sportathon.admin.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicateValueException.class, ValueNotPresentException.class, NullValueException.class, WrongValueException.class})
    public ResponseEntity<?> handleCustomException(Exception e){
        Response response = new Response(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
