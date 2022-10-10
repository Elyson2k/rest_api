package com.apiv1.testeapi.controller.exceptions;

import com.apiv1.testeapi.service.exceptions.DataIntegrityViolationException;
import com.apiv1.testeapi.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objNotFound(ObjectNotFoundException ex, HttpServletRequest request){
        StandardError err = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dtIntViolation(DataIntegrityViolationException ex, HttpServletRequest request){
        StandardError err = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
