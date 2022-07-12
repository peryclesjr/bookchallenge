package com.alten.challenge.bookchallenge.exception;


import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BookChallengeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        BookChallengeException error = new BookChallengeException(BookChallengeApiExceptionError.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .build());
        return new ResponseEntity<>(error.getApiError(), HttpStatus.NOT_FOUND);
    }


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BookChallengeException error = new BookChallengeException(BookChallengeApiExceptionError.builder()
                .message(ex.getMessage())
                .throwable(ex)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build());
        return new ResponseEntity<>(error.getApiError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookChallengeException.class)
    public ResponseEntity<Object> handleException(BookChallengeException ex) {
        return new ResponseEntity<>(ex.getApiError(), ex.getApiError().getStatus());
    }

}
