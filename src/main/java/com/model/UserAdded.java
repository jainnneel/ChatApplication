package com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class UserAdded implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    private UserEntity addedByUser;
   
    @OneToOne
    private UserEntity addedUser;

    public UserAdded() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
    public UserAdded(UserEntity addedByUser, UserEntity addedUser) {
        super();
        this.addedByUser = addedByUser;
        this.addedUser = addedUser;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(UserEntity addedByUser) {
        this.addedByUser = addedByUser;
    }

    public UserEntity getAddedUser() {
        return addedUser;
    }

    public void setAddedUser(UserEntity addedUser) {
        this.addedUser = addedUser;
    }

    
    
    
}
