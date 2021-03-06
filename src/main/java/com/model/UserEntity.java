package com.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    private String mobile;
    
    @JsonIgnore
    private String pass;
    
    @JsonIgnore
    private boolean isEnable;
    
    private Date dor;
    
    @ManyToMany(mappedBy = "entities",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<GroupChat> groupChat;
    
    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<GroupChat> groupadmin;
    
    @Transient
    private String status;
    
    private String lastseen;
    
    private String webpushToken;
    
    @OneToMany(mappedBy = "adminUser")
    @JsonIgnore
    private List<StockMarketGroup> stockGroupAdmin;
    
    @ManyToMany(mappedBy = "groupMembers",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<StockMarketGroup> stockGroups;
    
    @OneToMany(mappedBy = "entity",fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<GroupBooking> bookings;
    
    public UserEntity() {
        super();
    }
    public UserEntity(int id, String name, String mobile, String pass, Date dor) {
        super();
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.pass = pass;
        this.dor = dor;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public Date getDor() {
        return dor;
    }
    public void setDor(Date dor) {
        this.dor = dor;
    }
    public boolean isEnable() {
        return isEnable;
    }
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
    
    public List<GroupChat> getGroupChat() {
        return groupChat;
    }
    public void setGroupChat(List<GroupChat> groupChat) {
        this.groupChat = groupChat;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getLastseen() {
        return lastseen;
    }
    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }
    public String getWebpushToken() {
        return webpushToken;
    }
    public void setWebpushToken(String webpushToken) {
        this.webpushToken = webpushToken;
    }
    public List<GroupChat> getGroupadmin() {
        return groupadmin;
    }
    public void setGroupadmin(List<GroupChat> groupadmin) {
        this.groupadmin = groupadmin;
    }
    public List<StockMarketGroup> getStockGroupAdmin() {
        return stockGroupAdmin;
    }
    public void setStockGroupAdmin(List<StockMarketGroup> stockGroupAdmin) {
        this.stockGroupAdmin = stockGroupAdmin;
    }
    public List<StockMarketGroup> getStockGroups() {
        return stockGroups;
    }
    public void setStockGroups(List<StockMarketGroup> stockGroups) {
        this.stockGroups = stockGroups;
    }
    
    
}
