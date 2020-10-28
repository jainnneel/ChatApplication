package com.model;

public class MessageModel {
    private String fromLogin;
    private String toGroup;
    private String message;
    private String seenOrNot;
    private String chatId;
    private String date;
    private String toUser;
    private String status;
    
    public MessageModel() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MessageModel(String fromLogin, String message) {
        super();
        this.fromLogin = fromLogin;
        this.message = message;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getToUser() {
        return toUser;
    }
    public void setToUser(String toUser) {
        this.toUser = toUser;
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
    public String getSeenOrNot() {
        return seenOrNot;
    }
    public void setSeenOrNot(String seenOrNot) {
        this.seenOrNot = seenOrNot;
    }
    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
  
    
    
}
