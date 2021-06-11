package com.dto;

public class GroupMessageDto {

    private String id;
    
    private String fromLogin;
    
    private String message;
    
    private String date;
    
    private String groupChat;
    
    public GroupMessageDto() {
    }

    public GroupMessageDto(String id, String fromLogin, String message, String date, String groupChat) {
        super();
        this.id = id;
        this.fromLogin = fromLogin;
        this.message = message;
        this.date = date;
        this.groupChat = groupChat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(String groupChat) {
        this.groupChat = groupChat;
    }
    
    

}

