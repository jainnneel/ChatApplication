package com.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.StockMarketGroup;
import com.model.UserEntity;

@Repository
public interface StockGroupChat extends JpaRepository<StockMarketGroup, String> {

//    @Query(value = "select StockMarketGroup From StockMarketGroup where ")
//    List<StockMarketGroup> getAllGroupsByuser(UserEntity entity);

}
