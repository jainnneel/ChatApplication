package com.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.ChatMessage;

@Repository
public interface ChatMessageService extends JpaRepository<ChatMessage, String>{

    @Query("from ChatMessage where (fromMobile=?2 AND toMobile=?1) OR (fromMobile=?1 AND toMobile=?2)")
    List<ChatMessage> getAllMessageForUser(String tomobile,String fromMobile);

    @Query("from ChatMessage where toMobile=?1")
    List<ChatMessage> getAllMessageForGroup(String groupName);

}
