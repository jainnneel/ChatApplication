package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String message;
    private String fromMobile;
    private String toMobile;
    private String date;
    public ChatMessage() {
        super();
        // TODO Auto-generated constructor stub
    }
    public ChatMessage(String message, String fromMobile, String toMobile, String date) {
        super();
        this.message = message;
        this.fromMobile = fromMobile;
        this.toMobile = toMobile;
        this.date = date;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getFromMobile() {
        return fromMobile;
    }
    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }
    public String getToMobile() {
        return toMobile;
    }
    public void setToMobile(String toMobile) {
        this.toMobile = toMobile;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
