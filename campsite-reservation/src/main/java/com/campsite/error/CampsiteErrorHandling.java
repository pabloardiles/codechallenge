package com.campsite.error;

import com.campsite.api.InvalidDatesException;
import com.campsite.api.NoAvailabilityException;
import com.campsite.api.ReservationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CampsiteErrorHandling extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CampsiteErrorHandling.class);

    @ExceptionHandler(value = {NoAvailabilityException.class})
    protected ResponseEntity<Object> handleNoAvailError(RuntimeException ex, WebRequest request) {
        logger.error("Exception thrown: {}", ex.getMessage());
        CampsiteError response = new CampsiteError(ex.getMessage());
        return super.handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {OptimisticLockingFailureException.class})
    protected ResponseEntity<Object> handleOptLockingError(RuntimeException ex, WebRequest request) {
        String msg = "One or more dates are not available at this time. Try again later.";
        logger.error("Exception thrown: {}", msg);
        CampsiteError response = new CampsiteError(msg);
        return super.handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {InvalidDatesException.class})
    protected ResponseEntity<Object> handleDatesError(RuntimeException ex, WebRequest request) {
        logger.error("Exception thrown: {}", ex.getMessage());
        CampsiteError response = new CampsiteError(ex.getMessage());
        return super.handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ReservationNotFoundException.class})
    protected ResponseEntity<Object> handleResNotFoundError(RuntimeException ex, WebRequest request) {
        logger.error("Exception thrown: {}", ex.getMessage());
        CampsiteError response = new CampsiteError(ex.getMessage());
        return super.handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            String msg = "The request body content is invalid.";
            logger.error("Exception thrown: {}", msg);
            CampsiteError response = new CampsiteError(msg);
            return super.handleExceptionInternal(ex, response,
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else {
            return super.handleExceptionInternal(ex, body, headers, status, request);
        }
    }

}