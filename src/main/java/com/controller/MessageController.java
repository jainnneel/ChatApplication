package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.dao.AddingImpl;
import com.dao.ChastMessageImpl;
import com.dao.GroupChatImpl;
import com.dao.OfflineMessageImpl;
import com.dao.UserImpl;
import com.model.ChatMessage;
import com.model.GroupChat;
import com.model.MessageModel;
import com.model.OfflineMessage;
import com.model.OnlineStatus;
import com.model.UserEntity;
import com.websocketconfigre.WebSocketSessionListener;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private UserImpl userImpl;
    
    @Autowired
    private ChastMessageImpl chatimpl;
    
    @Autowired
    private AddingImpl addingImpl;
    
    @Autowired
    private OfflineMessageImpl offlineService;
    
    @Autowired
    private GroupChatImpl groupImpl;
    
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to,MessageModel messageModel) {
        UserEntity userByMobile = userImpl.getUserByMobile(to);
        
        chatimpl.createChatMessage(new ChatMessage(messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
        
        if(WebSocketSessionListener.getConnectedClientId().contains(to.trim())==true) {
        
            if(userByMobile!=null) {
                
                simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messageModel);
            }

        }else {
            
            offlineService.createMessage(new OfflineMessage(messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
        }
    }
        
    @MessageMapping("/group/{to}")
    public void sendMessageToGroupMember(@DestinationVariable String to,MessageModel messageModel) {
        GroupChat groupChat = groupImpl.getGroupByName(to.trim().toLowerCase());
        
        List<UserEntity> groupMembers =  userImpl.getAllUserInGroup(groupChat.getGid());
        
        chatimpl.createChatMessage(new ChatMessage(messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
        
        messageModel.setToGroup(to);
        
        for(UserEntity user:groupMembers) {
            
           if(!messageModel.getFromLogin().trim().equals(user.getMobile().trim())) {
            
               if(WebSocketSessionListener.getConnectedClientId().contains(user.getMobile().trim())==true) {

                System.out.println("send!!!!!!!!");
                
                simpMessagingTemplate.convertAndSend("/topic/groupMessage/"+user.getMobile(),messageModel);
            
            }else {
                
                offlineService.createMessage(new OfflineMessage(messageModel.getMessage(),messageModel.getToGroup(),user.getMobile(),messageModel.getDate()));
            }
          }
        }
    }
    
    @MessageMapping("/chat/delete/{to}")
    public void deleteMessage(@DestinationVariable String to,MessageModel messageModel) {
            simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messageModel);
    }
    
    @MessageMapping("/chat/read/{to}")
    public void readMessage(@DestinationVariable String to,MessageModel messageModel) {
            simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messageModel);
    }
    @MessageMapping("/chat/online")
    public void userOnline(@Payload UserEntity entity,SimpMessageHeaderAccessor headerAccessor ) {
        WebSocketSessionListener.setConnectedClientId(entity.getMobile());
        List<UserEntity> userList = addingImpl.getAllUserAddedByUser(entity.getMobile());
        List<String> mobileList = offlineService.getAllUserInformation(entity.getMobile());
        for(UserEntity u:userList) {
            simpMessagingTemplate.convertAndSend("/topic/status/"+u.getMobile(),new OnlineStatus(entity.getMobile(),"online"));
        }
        simpMessagingTemplate.convertAndSend("/topic/offlineMessage/"+entity.getMobile(),mobileList);
        headerAccessor.getSessionAttributes().put("username", entity.getMobile());
    }
    
    @MessageMapping("/chat/offlinemessage")
    public void deleteOfflineMessage(@Payload ChatMessage chatMessage) {
        offlineService.deleteOfflineMessage(chatMessage.getFromMobile(),chatMessage.getToMobile());
    }
    
    @MessageMapping("/chat/offlineGroupMessage")
    public void deleteofflineGroupMessage(@Payload ChatMessage chatMessage) {
        offlineService.deleteOfflineGroupMessage(chatMessage.getFromMobile(),chatMessage.getToMobile());
    }
}
