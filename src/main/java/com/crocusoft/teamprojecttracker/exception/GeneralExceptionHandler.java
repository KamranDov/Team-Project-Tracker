package com.crocusoft.teamprojecttracker.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class
GeneralExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ExceptionResponse registerException(UserRegistrationException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST
                , exception.getMessage());
    }

    @ExceptionHandler(RolePermissionException.class)
    public ExceptionResponse roleException(RolePermissionException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.FORBIDDEN.value()
                , HttpStatus.FORBIDDEN
                , exception.getMessage());
    }

}

