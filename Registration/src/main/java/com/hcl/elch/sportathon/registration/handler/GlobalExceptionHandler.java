package com.hcl.elch.sportathon.registration.handler;

import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import jakarta.mail.MessagingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log= LogManager.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
        log.error("Validation Error occurs ..");
        Map<String,String> mp=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->{
            mp.put(e.getField(), e.getDefaultMessage());
        });
        return mp;

    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(DataNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.error("Exception :- {}",response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DataAlreadyExist.class)
    public ResponseEntity<ErrorResponse> handleDataAlreadyExist(DataAlreadyExist ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        log.error("Exception :- {}",response.toString());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ConstraintViolationException.class,DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = "Duplicate Entry or data Integrity exception occurs..";
        ErrorResponse response=new ErrorResponse(HttpStatus.CONFLICT.value(),errorMessage);
        log.error("Exception :- {}",response.toString());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IOException.class, MessagingException.class})
    public ResponseEntity<?> handleMessagingException(Exception ex) {

        return new ResponseEntity<>("Exception occurs while sending mail or loading mail template", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        String errorMessage = "Some Exception occurs -: " + ex.getMessage();
        ErrorResponse response=new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),errorMessage);
        log.error("Exception :- {}",response.toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
