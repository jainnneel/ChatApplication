package com.model;

import org.springframework.http.HttpStatus;

public class ResponseEntity {

    private String status;
    private Object data;
    private String entity;
    
    private Exception exception;
    private HttpStatus httpStatus;
    private String token;
    public ResponseEntity() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public ResponseEntity(String status, Object data, String entity) {
        super();
        this.status = status;
        this.data = data;
        this.entity = entity;
    }

    public ResponseEntity(String status, Object data) {
        super();
        this.status = status;
        this.data = data;
    }



    public ResponseEntity(String status, Object data, String entity, Exception exception, HttpStatus httpStatus,
            String token) {
        super();
        this.status = status;
        this.data = data;
        this.entity = entity;
        this.exception = exception;
        this.httpStatus = httpStatus;
        this.token = token;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
        this.entity = entity;
    }
    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
    
}
