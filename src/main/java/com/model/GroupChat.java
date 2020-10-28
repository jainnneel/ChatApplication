package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GroupChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gid;

    private String groupName;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "gid"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private List<UserEntity> entities;
    
    @ManyToOne
    private UserEntity admin;
    
    private String typeOfGroup;
    
    public GroupChat() {
        super();
    }

    public GroupChat(int id, List<UserEntity> entities) {
        super();
        this.gid = id;
        this.entities = entities;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int id) {
        this.gid = id;
    }

    public List<UserEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<UserEntity> entities) {
        this.entities = entities;
    }
    

    public UserEntity getAdmin() {
        return admin;
    }

    public void setAdmin(UserEntity admin) {
        this.admin = admin;
    }

    public String getTypeOfGroup() {
        return typeOfGroup;
    }

    public void setTypeOfGroup(String typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    
}
