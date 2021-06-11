package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.MessagingSystem.pushnotification.FCMService;
import com.MessagingSystem.pushnotification.PushNotificationRequest;
import com.dao.AddingImpl;
import com.dao.ChastMessageImpl;
import com.dao.GroupChatImpl;
import com.dao.OfflineMessageImpl;
import com.dao.OfflineNotiImpl;
import com.dao.StockGroupImpl;
import com.dao.UserImpl;
import com.dto.GroupMessageDto;
import com.dto.MessageModelE2ee;
import com.model.ChatMessage;
import com.model.GroupChat;
import com.model.MessageModel;
import com.model.OfflineMessage;
import com.model.OnlineStatus;
import com.model.StockMarketGroup;
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
    
    @Autowired
    private OfflineNotiImpl offlineNotiImpl;
    
    @Autowired
    FCMService fcmService;
    
    @Autowired
    private StockGroupImpl marketGroup;
    
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to,@Payload MessageModel messageModel) {
        UserEntity userByMobile = userImpl.getUserByMobile(to);
        UserEntity sender = userImpl.getUserByMobile(messageModel.getFromLogin());
        System.out.println("message to   " + to +"  from  "+ messageModel.getFromLogin());
        System.out.println(WebSocketSessionListener.getConnectedClientId());
        if (userByMobile.getWebpushToken()!=null && !userByMobile.getWebpushToken().equals("")) {
            sendNotification(userByMobile.getWebpushToken(), "" , "you have a message from "+sender.getName());
        }
        System.out.println(WebSocketSessionListener.getConnectedClientId());
        if(WebSocketSessionListener.getConnectedClientId().contains(to.trim())==true) {

            if(userByMobile!=null) {
                ChatMessage createChatMessage = chatimpl.createChatMessage(new ChatMessage(messageModel.getChatId(),messageModel.getSeenOrNot(),messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
                messageModel.setChatId(createChatMessage.getId());
                messageModel.setToUser(to);
                simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messageModel);
            }

        }else {
            chatimpl.createChatMessage(new ChatMessage(messageModel.getChatId(),messageModel.getSeenOrNot(),messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
            offlineService.createMessage(new OfflineMessage(messageModel.getChatId(), messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
            
        }
    }
    
    @MessageMapping("/chat/etoee/{to}")
    public void sendE2ee(@DestinationVariable String to,@Payload MessageModelE2ee messageModel) {
        System.out.println(messageModel.toString());
        simpMessagingTemplate.convertAndSend("/topic/messages/e2ee/"+to,messageModel);
    }
        
    @MessageMapping("/group/{to}")
    public void sendMessageToGroupMember(@DestinationVariable String to,MessageModel messageModel) {
        GroupChat groupChat = groupImpl.getGroupByName(to.trim().toLowerCase());
        
        List<UserEntity> groupMembers =  userImpl.getAllUserInGroup(groupChat.getGid());
        
        chatimpl.createChatMessage(new ChatMessage(messageModel.getChatId(),"",messageModel.getMessage(),messageModel.getFromLogin(),to,messageModel.getDate()));
        
        messageModel.setToGroup(to);
        
        for(UserEntity user:groupMembers) {
            
           if(!messageModel.getFromLogin().trim().equals(user.getMobile().trim())) {
            
               if(WebSocketSessionListener.getConnectedClientId().contains(user.getMobile().trim())==true) {

                System.out.println("send!!!!!!!!");
                
                simpMessagingTemplate.convertAndSend("/topic/groupMessage/"+user.getMobile(),messageModel);

               }else {
                
                offlineService.createMessage(new OfflineMessage(messageModel.getChatId(),messageModel.getMessage(),messageModel.getToGroup(),user.getMobile(),messageModel.getDate()));
            }
          }
        }
    }
    
  
    @MessageMapping("/chat/group/{groupId}")
    public void sendGroupMessage(@DestinationVariable("groupId") String groupid ,@Payload MessageModel messageModel) {
            StockMarketGroup group =  marketGroup.getGroupById(groupid);
            List<UserEntity> entities = marketGroup.getAllGroupMembers(groupid);
            chatimpl.createChatMessage(new ChatMessage(messageModel.getChatId(),"",messageModel.getMessage(),messageModel.getFromLogin(),groupid,messageModel.getDate()));
            for(UserEntity user:entities) {
                
                if(!messageModel.getFromLogin().trim().equals(user.getMobile().trim())) {
                 
                    if(WebSocketSessionListener.getConnectedClientId().contains(user.getMobile().trim())==true) {

                     System.out.println("send!!!!!!!!");
                     messageModel.setForgroup(true);
                     simpMessagingTemplate.convertAndSend("/topic/messages/"+user.getMobile(),messageModel);

                    }else {
                     
                     offlineService.createMessage(new OfflineMessage(messageModel.getChatId(),messageModel.getMessage(),messageModel.getToGroup(),user.getMobile(),messageModel.getDate()));
                 }
               }
             }
    }
    
    
    @MessageMapping("/chat/delete/{to}")
    public void deleteMessage(@DestinationVariable String to,MessageModel messageModel) {
            simpMessagingTemplate.convertAndSend("/topic/deletemessage/"+to,messageModel);
    }
    
    @MessageMapping("/chat/read/{to}")
    public void readMessage(@DestinationVariable String to,MessageModel messageModel) {
            System.out.println(messageModel.getChatId()+"dsaaaaaaaaaaa");
            simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messageModel);
    }
    @MessageMapping("/chat/online")
    public void userOnline(@Payload UserEntity entity,SimpMessageHeaderAccessor headerAccessor ) {
        System.out.println("dsada");
        WebSocketSessionListener.setConnectedClientId(entity.getMobile());
        List<UserEntity> userList = addingImpl.getAllUserAddedByUser(entity.getMobile());
        boolean noti = offlineNotiImpl.getnotification(entity.getMobile().trim()); 
        List<String> userMobile = chatimpl.getAllUnSeenMessagesForUser(entity.getMobile());
        
//      List<String> mobileList = offlineService.getAllUserInformation(entity.getMobile());
        for(UserEntity u:userList) {
            System.out.println(u.getMobile());
            simpMessagingTemplate.convertAndSend("/topic/status/"+u.getMobile(),new OnlineStatus(entity.getMobile(),"online"));
        }
        simpMessagingTemplate.convertAndSend("/topic/offlineMessage/"+entity.getMobile(),userMobile);   
        if(noti) {
        simpMessagingTemplate.convertAndSend("/topic/notimessages/"+entity.getMobile(),noti);
        }
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
    
    @MessageMapping("/chat/readMessage")
    public void readMessage(@Payload ChatMessage chatMessage) {
        List<ChatMessage> setReadReciept = chatimpl.setReadReciept(chatMessage);
        simpMessagingTemplate.convertAndSend("/topic/readMessages/"+chatMessage.getFromMobile(),setReadReciept);
    }
    
    @MessageMapping("/chat/readMessageNoti")
    public void readMessageNoti(@Payload ChatMessage chatMessage) {
        offlineNotiImpl.setAllNotiOfUserSeen(chatMessage.getFromMobile());
    }
    
    
    
    private boolean sendNotification(String token,String message,String title) {
        PushNotificationRequest pushNotificationRequest=new PushNotificationRequest();
        pushNotificationRequest.setMessage(message);
        pushNotificationRequest.setTitle(title);
        pushNotificationRequest.setToken(token);
        Map<String, String> appData= new HashMap<>();
            appData.put("name", "PushNotification");
        try {
            fcmService.sendMessage(appData, pushNotificationRequest);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}
