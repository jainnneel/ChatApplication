package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ChatMessage;
import com.service.ChatMessageService;

@Service
public class ChastMessageImpl {
    
    @Autowired
    ChatMessageService chatMessageService;
    
    public void createChatMessage(ChatMessage chatMessage) {
        try {
            chatMessageService.save(chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ChatMessage> getAllMessageForUser(String tomobile,String fromMobile){
        List<ChatMessage> chatMessages = null;
        try {
            chatMessages =  chatMessageService.getAllMessageForUser(tomobile,fromMobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessages;
    }
    
    public List<ChatMessage> getAllMessageForGroup(String groupName){
        List<ChatMessage> chatMessages = null;
        try {
            chatMessages =  chatMessageService.getAllMessageForGroup(groupName.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessages;
    }

    public boolean deletemessage(String messageid) {
        boolean result=false;
        try {
           chatMessageService.deleteById(messageid.trim());
           result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
