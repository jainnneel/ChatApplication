package com.dto;

public class ChatBody {

    private String type;
    
    private String body;
    
    private String registrationId;

    public ChatBody() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ChatBody(String type, String body, String registrationId) {
        super();
        this.type = type;
        this.body = body;
        this.registrationId = registrationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "ChatBody [type=" + type + ", body=" + body + ", registrationId=" + registrationId + "]";
    }
    
    
    
}
