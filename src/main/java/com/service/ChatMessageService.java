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
    
    @Query("from ChatMessage where (fromMobile=?1 AND toMobile=?2) AND (seenOrNot=?3 OR seenOrNot=?4)")
    List<ChatMessage> getAllChatMessages(String fromMobile, String toMobile,String seen,String seen2);

    @Query("select fromMobile from ChatMessage where toMobile=?1 AND seenOrNot=?2")
    List<String> getAllUnSeenMessagesForUser(String mobile,String status);


}
