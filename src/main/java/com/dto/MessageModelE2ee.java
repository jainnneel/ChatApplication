package com.dto;
//at [Source: (byte[])"{"fromLogin":"955","message":{"type":3,"body":"3(Û>\b¦\u000b0£\u000b\u0012!\u0005JnÈ\u0011Â\"j½/W^²\u0014½8$=ë\"Åý¤s¶ñQ`\u001a!\u0005ó&é\u0006$zUE\u001aø.À0%æÛ\\w+4\u0002×ÿUÈæÜw.\"B3\n!\u0005Ù\u0007m\u001a&\u0007tË\\\\Ýð0á¾:¿@\u0011[aÞ\nGÇX~Ã_\u0010\u0003\u0018\u0000\"\u0010W{óí~ºvÎ'k.¡\u0010ç\r¶ú-_Ô","registrationId":1442},"date":"4:21 PM","chatId":10921076740542384,"toUser":"954"}"; line: 1, column: 30] (through reference chain: com.model.MessageModel["message"])

public class MessageModelE2ee {
    
    private String fromLogin;
    
    private ChatBody message;
    
    private String date;
    
    private String chatId;
    
    private String toUser;

    public MessageModelE2ee() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MessageModelE2ee(String fromLogin, ChatBody message, String date, String chatId, String toUser) {
        super();
        this.fromLogin = fromLogin;
        this.message = message;
        this.date = date;
        this.chatId = chatId;
        this.toUser = toUser;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    public ChatBody getMessage() {
        return message;
    }

    public void setMessage(ChatBody message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return "MessageModelE2ee [fromLogin=" + fromLogin + ", message=" + message + ", date=" + date + ", chatId="
                + chatId + ", toUser=" + toUser + "]";
    }
    
    
}
