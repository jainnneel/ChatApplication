package com.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StockMarketGroup {

    @Id
    private String groupId;
    
    private String groupName;
    
    private String groupDescription;
    
    private String price;
    
    private int noMembers;
    
    private Date dateOfCreation;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private UserEntity adminUser;
    
    private boolean freeOrNot;
    
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(name = "stockGroup",joinColumns = @JoinColumn(name = "stockMarket_id", referencedColumnName = "groupId"),inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> groupMembers;

    @OneToMany(mappedBy = "groupChat",cascade = {CascadeType.PERSIST})
    private List<GroupChatMessages> chatMessages;
    
    public StockMarketGroup() {
        super();
    }

    public StockMarketGroup(String groupId, String groupName, String groupDescription, String price, int noMembers,
            Date dateOfCreation, UserEntity adminUser, boolean freeOrNot, List<UserEntity> groupMembers) {
        super();
        this.groupId = groupId; 
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.price = price;
        this.noMembers = noMembers;
        this.dateOfCreation = dateOfCreation;
        this.adminUser = adminUser;
        this.freeOrNot = freeOrNot;
        this.groupMembers = groupMembers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNoMembers() {
        return noMembers;
    }

    public void setNoMembers(int noMembers) {
        this.noMembers = noMembers;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public UserEntity getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(UserEntity adminUser) {
        this.adminUser = adminUser;
    }

    public boolean isFreeOrNot() {
        return freeOrNot;
    }

    public void setFreeOrNot(boolean freeOrNot) {
        this.freeOrNot = freeOrNot;
    }

    public List<UserEntity> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<UserEntity> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Override
    public String toString() {
        return "StockMarketGroup [groupId=" + groupId + ", groupName=" + groupName + ", groupDescription="
                + groupDescription + ", price=" + price + ", noMembers=" + noMembers + ", dateOfCreation="
                + dateOfCreation + ", adminUser=" + adminUser + ", freeOrNot=" + freeOrNot + ", groupMembers="
                + groupMembers + "]";
    }

   
}
