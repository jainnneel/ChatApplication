package com.dto;

public class JoinGroupRequst {

    private String groupId;

    public JoinGroupRequst() {
        super();
        // TODO Auto-generated constructor stub
    }

    public JoinGroupRequst(String groupId) {
        super();
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "JoinGroupRequst [groupId=" + groupId + "]";
    }
    
}
