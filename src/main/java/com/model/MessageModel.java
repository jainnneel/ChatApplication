package com.model;

public class MessageModel {
    private String fromLogin;
    private String toGroup;
    private String message;
    private String date;
    
    public MessageModel() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MessageModel(String fromLogin, String message) {
        super();
        this.fromLogin = fromLogin;
        this.message = message;
    }
    public String getFromLogin() {
        return fromLogin;
    }
    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
  
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getToGroup() {
        return toGroup;
    }
    public void setToGroup(String toGroup) {
        this.toGroup = toGroup;
    }
    @Override
    public String toString() {
        return "MessageModel [fromLogin=" + fromLogin + ", toGroup=" + toGroup + ", message=" + message + ", date="
                + date + "]";
    }
  
    
    
}
