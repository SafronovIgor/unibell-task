package ru.unibell.task.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                new ErrorResponse.ErrorDetail(ex.getReason(), ex.getStatusCode().value())
        );

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private ErrorDetail error;

        @Data
        @AllArgsConstructor
        public static class ErrorDetail {
            private String title;
            private int status;
        }
    }
}