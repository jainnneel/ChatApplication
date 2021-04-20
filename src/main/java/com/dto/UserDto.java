package com.dto;

public class UserDto {

    
    private String username;
    private String mobile;
    private String pass;
    public UserDto() {
        super();
        // TODO Auto-generated constructor stub
    }
    public UserDto(String username, String mobile, String pass) {
        super();
        this.username = username;
        this.mobile = mobile;
        this.pass = pass;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
}
