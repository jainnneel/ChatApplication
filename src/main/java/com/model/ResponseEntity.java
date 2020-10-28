package com.model;

import java.util.Map;

public class ResponseEntity {

    private String status;
    private Object data;
    private String entity;
    
    public ResponseEntity() {
        super();
        // TODO Auto-generated constructor stub
    }
    public ResponseEntity(String status, Object data,String entity) {
        super(); 
        this.status = status;
        this.data = data;
        this.entity= entity;
    }
    
    
    
    public ResponseEntity(String status, Object data) {
        super();
        this.status = status;
        this.data = data;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
        this.entity = entity;
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
    
    
    
}
