package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.UserAdded;
import com.model.UserEntity;

@Repository
public interface AddingService extends JpaRepository<UserAdded,Integer> {

    @Query("from UserAdded where addedByUser=?1 and addedUser=?2")
    UserAdded checkAreadyAddedOrNot(UserEntity addedByUser, UserEntity addedUser);

    @Query("select addedUser from UserAdded where added_by_user_id=?1")
    List<UserEntity> getAllUserAddedByUser(UserEntity userByMobile);

    @Query("delete from UserAdded where addedByUser=?1 AND addedUser=?2")
    @Transactional
    @Modifying
    void deleteusermapping(UserEntity addedByUser, UserEntity addedUser);

}
