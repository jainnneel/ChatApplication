package com.model;

public class ResponseEntity {

    private String status;
    private Object data;
    public ResponseEntity() {
        super();
        // TODO Auto-generated constructor stub
    }
    public ResponseEntity(String status, Object data) {
        super();
        this.status = status;
        this.data = data;
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
