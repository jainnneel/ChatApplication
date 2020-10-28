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
    
    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        ChatMessage chatMessage2=null;
        try {
            chatMessage2 =   chatMessageService.save(chatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessage2;
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

    public List<ChatMessage> setReadReciept(ChatMessage chatMessage) {
        List<ChatMessage> chatMessages=null;
        try {
            chatMessages = chatMessageService.getAllChatMessages(chatMessage.getFromMobile(),chatMessage.getToMobile(),"send","received");
            for(ChatMessage chat: chatMessages) {
                chat.setSeenOrNot("seen");
            }
            chatMessageService.saveAll(chatMessages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessages;
    }

    public List<String> getAllUnSeenMessagesForUser(String mobile) {
        List<String> mobileList = null;
        try {
            mobileList =chatMessageService.getAllUnSeenMessagesForUser(mobile,"send");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mobileList;
    }
}
