package com.crocusoft.teamprojecttracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TeamDeleteByUsersException extends RuntimeException {
    public TeamDeleteByUsersException(String message) {
        super(message);
    }
}
