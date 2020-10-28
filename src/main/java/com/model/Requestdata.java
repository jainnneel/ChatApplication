package com.model;

public class Requestdata {

    private String gid;
    private String uid;
    private String uid2;
    
    public Requestdata() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Requestdata(String gid, String uid) {
        super();
        this.gid = gid;
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Requestdata(String gid, String uid, String uid2) {
        super();
        this.gid = gid;
        this.uid = uid;
        this.uid2 = uid2;
    }

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }
    
    
}
