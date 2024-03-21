package com.example.crm.payload.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<String> content;


    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus status, String message, List<String> content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
