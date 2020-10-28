package com.model;

import java.util.List;

public class GroupCreateRequest {

    private String groupName;
    private List<String> entities;
    private String typeOfGroup;
    
    public GroupCreateRequest(String groupName, List<String> entities, String typeOfGroup) {
        super();
        this.groupName = groupName;
        this.entities = entities;
        this.typeOfGroup = typeOfGroup;
    }
    public GroupCreateRequest() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public List<String> getEntities() {
        return entities;
    }
    public void setEntities(List<String> entities) {
        this.entities = entities;
    }
    public String getTypeOfGroup() {
        return typeOfGroup;
    }
    public void setTypeOfGroup(String typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }
    
    
}
