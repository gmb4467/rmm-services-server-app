package com.rmm.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class ControllerAdviceConfig extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(Exception exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflict(Exception exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }
}
