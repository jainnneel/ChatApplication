package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ChatMessage;
import com.model.MessageModel;
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
    
    public List<MessageModel> getAllMessageForUser(String tomobile,String fromMobile){
        List<ChatMessage> chatMessages = null;
        System.out.println(tomobile.substring(1,tomobile.length()-1));
        
        try {
            chatMessages =  chatMessageService.getAllMessageForUser(tomobile.substring(1,tomobile.length()-1),fromMobile);
            System.out.println(chatMessages);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getMessageModel(chatMessages);
    }
    
    private List<MessageModel> getMessageModel(List<ChatMessage> chatMessages) {
        List<MessageModel> list= new ArrayList<>();
        chatMessages.forEach(chat -> {
            MessageModel messageModel = new MessageModel();
            messageModel.setChatId(chat.getId());
            messageModel.setFromLogin(chat.getFromMobile());
            messageModel.setToUser(chat.getToMobile());
            messageModel.setSeenOrNot(chat.getSeenOrNot());
            messageModel.setMessage(chat.getMessage());
            messageModel.setDate(chat.getDate());
            list.add(messageModel);
        });
        return list;
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

    public ChatMessage getChatmessage(String messageid) {
        return chatMessageService.findById(messageid).get();
    }
}
