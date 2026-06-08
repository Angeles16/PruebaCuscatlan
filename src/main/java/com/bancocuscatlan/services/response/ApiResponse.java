package com.bancocuscatlan.services.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private Object errors;

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Object getErrors() { return errors; }

    public ApiResponse(int status, String message, T data, Object errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse<>(status.value(), message, data, null), status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(int status, String message, Object errors) {
        return new ResponseEntity<>(new ApiResponse<>(status, message, null, errors), HttpStatus.valueOf(status));
    }
}
