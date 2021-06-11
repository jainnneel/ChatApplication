package com.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.GroupChatMessages;

@Repository
public interface GroupChatMessageRepo  extends JpaRepository<GroupChatMessages, String> {

    @Query(value = "select chm from GroupChatMessages as chm JOIN chm.groupChat as gm where gm.groupId=?1")
    List<GroupChatMessages> getAllChatsByGroup(String group);

}
