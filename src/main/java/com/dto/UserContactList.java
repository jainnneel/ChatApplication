package com.dto;

import java.util.List;

import com.model.StockMarketGroup;
import com.model.UserEntity;

public class UserContactList {

    private List<UserEntity> entities;
    
    private List<StockMarketGroup> marketGroups;

    public UserContactList() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserContactList(List<UserEntity> entities, List<StockMarketGroup> marketGroups) {
        super();
        this.entities = entities;
        this.marketGroups = marketGroups;
    }

    public List<UserEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<UserEntity> entities) {
        this.entities = entities;
    }

    public List<StockMarketGroup> getMarketGroups() {
        return marketGroups;
    }

    public void setMarketGroups(List<StockMarketGroup> marketGroups) {
        this.marketGroups = marketGroups;
    }
    
    
}
