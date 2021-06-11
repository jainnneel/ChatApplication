package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GroupChatMessages {

    @Id
    private String id;
    
    private String message;
    
    private String fromMobile;
    
    @ManyToOne
    private StockMarketGroup groupChat;
    
    private String date;
    
    public GroupChatMessages() {
        super();
    }

    public GroupChatMessages(String id, String message, String fromMobile, StockMarketGroup groupChat, String date) {
        super();
        this.id = id;
        this.message = message;
        this.fromMobile = fromMobile;
        this.groupChat = groupChat;
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

    public StockMarketGroup getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(StockMarketGroup groupChat) {
        this.groupChat = groupChat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
