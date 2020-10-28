package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OfflineNotifiacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nid;
    private String message;
    private String tofromMobile;
    private String fromMobile;
    private String type;
    private boolean seenOrNot;
    
    
    public OfflineNotifiacation() {
        super();
        // TODO Auto-generated constructor stub
    }

    public OfflineNotifiacation(String message, String tofromMobile, String fromMobile,String type,boolean seenOrNot) {
        super();
        this.message = message;
        this.tofromMobile = tofromMobile;
        this.fromMobile = fromMobile;
        this.type=type;
        this.seenOrNot=seenOrNot;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTofromMobile() {
        return tofromMobile;
    }

    public void setTofromMobile(String tofromMobile) {
        this.tofromMobile = tofromMobile;
    }

    public String getFromMobile() {
        return fromMobile;
    }

    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public boolean getSeenOrNot() {
        return seenOrNot;
    }

    public void setSeenOrNot(boolean seenOrNot) {
        this.seenOrNot = seenOrNot;
    }

    @Override
    public String toString() {
        return "OfflineNotifiacation [nid=" + nid + ", message=" + message + ", tofromMobile=" + tofromMobile
                + ", fromMobile=" + fromMobile + "]";
    }
   
}
