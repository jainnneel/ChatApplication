package com.model;

public class OnlineStatus {

    private String mobile;
    private String online;
    public OnlineStatus() {
        super();
        // TODO Auto-generated constructor stub
    }
    public OnlineStatus(String mobile, String online) {
        super();
        this.mobile = mobile;
        this.online = online;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getOnline() {
        return online;
    }
    public void setOnline(String online) {
        this.online = online;
    }
    
    
}
