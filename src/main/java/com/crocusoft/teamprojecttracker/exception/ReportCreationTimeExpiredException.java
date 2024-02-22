package com.crocusoft.teamprojecttracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ReportCreationTimeExpiredException extends RuntimeException {
    public ReportCreationTimeExpiredException(String message) {
        super(message);
    }
}
