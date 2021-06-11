package com.dto;

public class GroupRequestDto {

    private String groupName;
    
    private String desc;
    
    private String price;
    
    private boolean freeOrNot;

    public GroupRequestDto() {
        super();
    }

    public GroupRequestDto(String groupName, String desc, String price, boolean freeOrNot) {
        super();
        this.groupName = groupName;
        this.desc = desc;
        this.price = price;
        this.freeOrNot = freeOrNot;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isFreeOrNot() {
        return freeOrNot;
    }

    public void setFreeOrNot(boolean freeOrNot) {
        this.freeOrNot = freeOrNot;
    }

    @Override
    public String toString() {
        return "GroupRequestDto [groupName=" + groupName + ", desc=" + desc + ", price=" + price + ", freeOrNot="
                + freeOrNot + "]";
    }
    
}
