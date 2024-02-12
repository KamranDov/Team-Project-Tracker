package com.crocusoft.teamprojecttracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RolePermissionException extends Exception {
    public RolePermissionException(String message) {
        super(message);
    }
}
