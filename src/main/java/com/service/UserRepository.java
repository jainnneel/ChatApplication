package com.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByMobile(String mobile);
    
    @Query(value = "select userentity from UserEntity userentity JOIN userentity.groupChat gc where gc.id=?1")
    List<UserEntity> getAllUserInGroup(int groupChat);


}
