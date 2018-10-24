package com.campsite.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class CampsiteErrorHandling extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CampsiteErrorHandling.class);

    @ExceptionHandler(value = {IllegalArgumentException.class, DateTimeParseException.class})
    protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
        logger.error("Exception thrown: ", ex.getMessage());
        CampsiteError response = new CampsiteError(ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}