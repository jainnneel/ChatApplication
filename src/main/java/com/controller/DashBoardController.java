package com.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.AddingImpl;
import com.dao.ChastMessageImpl;
import com.dao.GroupChatImpl;
import com.dao.OfflineNotiImpl;
import com.dao.UserImpl;
import com.model.ChatMessage;
import com.model.GroupChat;
import com.model.GroupCreateRequest;
import com.model.MessageModel;
import com.model.OfflineNotifiacation;
import com.model.Requestdata;
import com.model.ResponseEntity;
import com.model.UserAdded;
import com.model.UserEntity;
import com.websocketconfigre.WebSocketSessionListener;

@Controller
public class DashBoardController {

    @Autowired
    private UserImpl userImpl;

    @Autowired
    private AddingImpl addingImpl;
    
    @Autowired
    private ChastMessageImpl chatImpl;
    
    @Autowired
    private GroupChatImpl groupChatImpl;
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private OfflineNotiImpl offlineNotiImpl;

   

    @RequestMapping(value = "/home")
    public String homePage(Model m, @AuthenticationPrincipal UserDetails details) {
        UserEntity entity = userImpl.getUserByMobile(details.getUsername());
        m.addAttribute("user", entity);
        return "home";
    }

    @RequestMapping(value = "adduser", consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addUser(@RequestBody Object mobile, @AuthenticationPrincipal UserDetails details) {
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        UserEntity addedUser = userImpl.getUserByMobile(mobile.toString());
        boolean existOrnot = addingImpl.checkAreadyAddedOrNot(addedByUser, addedUser);
        if (addedUser != null) {
            if (addedByUser == addedUser || existOrnot) {
                return new ResponseEntity("alreadyAdded", null);
            } else {
                if(WebSocketSessionListener.getConnectedClientId().contains(mobile.toString().trim())==true) {
                    simpMessagingTemplate.convertAndSend("/topic/notimessages/"+mobile.toString(),new MessageModel());
                    offlineNotiImpl.createOfflineMessage(new OfflineNotifiacation(addedByUser.getName()+" ( "+addedByUser.getMobile()+" ) "+"send request",mobile.toString(),addedByUser.getMobile(),"req",false));
                    return new ResponseEntity("done", null);
                }else {
                    offlineNotiImpl.createOfflineMessage(new OfflineNotifiacation(addedByUser.getName()+"("+addedByUser.getMobile()+")"+"send request",mobile.toString(),addedByUser.getMobile(),"req",false));
                    return new ResponseEntity("done", null);
                }
            }
        } else {
            return new ResponseEntity("not exists", null);
        }
    }
    
    @RequestMapping(value = "addUseToContact", consumes = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addUseToContact(@RequestBody Object mobile, @AuthenticationPrincipal UserDetails details) {
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        UserEntity addedUser = userImpl.getUserByMobile(mobile.toString());
        addingImpl.createUser(new UserAdded(addedByUser, addedUser));
        addingImpl.createUser(new UserAdded(addedUser, addedByUser));
        offlineNotiImpl.deleteNotificationForUser(addedUser.getMobile().trim(),addedByUser.getMobile().trim());
        offlineNotiImpl.createOfflineMessage(new OfflineNotifiacation(addedByUser.getName()+" ( "+addedByUser.getMobile()+" ) "+"accept your request",mobile.toString(),addedByUser.getMobile(),"acce",false));
        simpMessagingTemplate.convertAndSend("/topic/notimessage/"+mobile.toString(),addedByUser);
        return new ResponseEntity("added", null);
    }
    
    @RequestMapping(value = "addgroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addgroup(@RequestBody Object groupName, @AuthenticationPrincipal UserDetails details) {
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        GroupChat groupChat = groupChatImpl.getGroupByName(groupName.toString().trim());
        if(groupChat.getTypeOfGroup().trim().equalsIgnoreCase("public")) {
            if(groupChat!=null) {
                String resp = userImpl.addUserToGroup(addedByUser,groupChat);
                return new ResponseEntity(resp+groupName.toString(), null);
            }else {
               return new ResponseEntity("No Group found", null);
            }
        }else {
            return new ResponseEntity("This is private group.", null);
        }
        
       
    }
    
    @RequestMapping(value = "getAllUserAddedByUser",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public List<UserEntity> getAllUserAddedByUser(@RequestBody Object mobile){
        List<UserEntity> listEntity = addingImpl.getAllUserAddedByUser(mobile.toString());
        for(UserEntity user:listEntity) {
            if(WebSocketSessionListener.getConnectedClientId().contains(user.getMobile())==true) {
                System.out.println(user.getMobile()+"onlineonline");
                user.setStatus("   online");
            }
            else{
                user.setStatus("");
            }
        }
        return addingImpl.getAllUserAddedByUser(mobile.toString());
    }
    
    @RequestMapping(value = "getAllGroupAddedByUser",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public List<GroupChat> getAllGroupAddedByUser(@RequestBody Object mobile){
        List<GroupChat> listGroups = groupChatImpl.getGroupListByUserEntity(userImpl.getUserByMobile(mobile.toString()));
        return listGroups;
    }   
    
    @RequestMapping(value = "getUserChat",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public List<ChatMessage> getUserChat(@RequestBody Object userName,@AuthenticationPrincipal UserDetails details){
        return chatImpl.getAllMessageForUser(userName.toString(),details.getUsername());
    }
    
    @RequestMapping(value = "getGroupChat",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public List<ChatMessage> getGroupChat(@RequestBody Object groupName){
        System.out.println(groupName.toString());
        return chatImpl.getAllMessageForGroup(groupName.toString());
    }
    
    @RequestMapping(value = "deleteuser",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteMessage(@RequestBody Object messageid){
        if(chatImpl.deletemessage(messageid.toString())) {
            return new ResponseEntity("done", null);
        }else {
            return new ResponseEntity("not", null);
        }
    }
   
    @RequestMapping(value = "deniedrequest",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteNotification(@RequestBody Object mobile, @AuthenticationPrincipal UserDetails details){
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        UserEntity addedUser = userImpl.getUserByMobile(mobile.toString());
        offlineNotiImpl.deleteNotificationForUser(addedUser.getMobile().trim(),addedByUser.getMobile().trim());
        return new ResponseEntity("done", null);
    }
    
    @RequestMapping(value = "getNotification",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getNotification(@RequestBody Object mobile){
        List<OfflineNotifiacation> notification = offlineNotiImpl.getallNotificationofuser(mobile.toString());
        return new ResponseEntity("done", notification);
    }
    
    @RequestMapping(value = "deleteNotification",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteNotification(@RequestBody Object nid){
        offlineNotiImpl.deletenotification(Integer.parseInt(nid.toString().trim()));
        return new ResponseEntity("done", null);
    }
    
    @RequestMapping(value = "getUserListNotMember",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getUserListNotMember(@RequestBody Object gname, @AuthenticationPrincipal UserDetails details){
            GroupChat groupChat = groupChatImpl.getGroupByName(gname.toString().trim());
            List<UserEntity> userEntities = groupChatImpl.getNotAddedMembers(groupChat,details.getUsername());
            return new ResponseEntity("done", userEntities);
    }
    
    @RequestMapping(value = "addmemgroup",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addmemgroup(@RequestBody GroupCreateRequest group,@AuthenticationPrincipal UserDetails details){
        GroupChat groupChat = groupChatImpl.getGroupByName(group.getGroupName());
        if(groupChat!=null) {
            UserEntity userEntity = userImpl.getUserByMobile(details.getUsername());
            for(String entity:group.getEntities()) {
                UserEntity addedUser = userImpl.getUserByMobile(Integer.parseInt(entity.split("/")[0]));
                 groupChatImpl.addUserToGroup(userImpl.getUserByMobile(Integer.parseInt(entity.split("/")[0])),groupChat);
                 offlineNotiImpl.createOfflineMessage(new OfflineNotifiacation("added in group "+group.getGroupName() + " By "+userEntity.getName(),addedUser.getMobile(),details.getUsername().toString(),"add",false));
                 simpMessagingTemplate.convertAndSend("/topic/notimessage/"+addedUser.getMobile(),"done");
            }
                return new ResponseEntity("done",null);
        }else {
               return new ResponseEntity("No Group found", null);
        }
    }
    
    @RequestMapping(value = "createGroupForm",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createGroupForm(@RequestBody GroupCreateRequest group, @AuthenticationPrincipal UserDetails details){
        GroupChat chat =  groupChatImpl.getGroupByName(group.getGroupName().trim());
        if(chat!=null) {
            return new ResponseEntity("Group name must be unique", null);
        }else {
        UserEntity userEntity = userImpl.getUserByMobile(details.getUsername());
        ModelMapper mapper = new ModelMapper();
        List<UserEntity> entities = new  ArrayList<>();
        for(String id:group.getEntities()) {
            UserEntity addedUser = userImpl.getUserByMobile(Integer.parseInt(id.split("/")[0]));
            entities.add(addedUser);
            offlineNotiImpl.createOfflineMessage(new OfflineNotifiacation("added in group "+group.getGroupName() + " By "+userEntity.getName(),addedUser.getMobile(),details.getUsername().toString(),"add",false));
            simpMessagingTemplate.convertAndSend("/topic/notimessage/"+addedUser.getMobile(),"done");
        }
        entities.add(userEntity);
        System.out.println(entities);
        GroupChat groupchat =  mapper.map(group, GroupChat.class);
        groupchat.setAdmin(userEntity);
        groupchat.setEntities(entities);
        groupChatImpl.createGroup(groupchat);
//        userImpl.addingUserListToGroup(entities,groupchat);
        return new ResponseEntity("done", null);
     }
    }  
    
    @RequestMapping(value = "getGroupsOfUserAdminAndPublic",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getGroupsOfUserAdminAndPublic(@RequestBody Object mobile){
        HashSet<GroupChat> chats = groupChatImpl.getGroupsOfUserAdminAndPublic(mobile);
        return new ResponseEntity("done",chats);
    }
    
    @RequestMapping(value = "showgrouplist",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity showgrouplist(@RequestBody Object gid){
        return new ResponseEntity("done",userImpl.getAllUserInGroup(Integer.parseInt(gid.toString().trim())),groupChatImpl.getAdminByGroupId(gid.toString()).getMobile());
    }
    
    @RequestMapping(value = "removeuser",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeuser(@RequestBody Object gid,@AuthenticationPrincipal UserDetails details){
        return new ResponseEntity("done",addingImpl.removeuserfromdata(userImpl.getUserByMobile(Integer.parseInt(gid.toString().trim())),userImpl.getUserByMobile(details.getUsername())));
    }
    
    @RequestMapping(value = "groupleft",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity groupleft(@RequestBody Object gid,@AuthenticationPrincipal UserDetails details){
        groupChatImpl.removeuserfromgroup(userImpl.getUserByMobile(details.getUsername()), Integer.parseInt(gid.toString().trim()));    
        return new ResponseEntity("done","");
    }
    
    @RequestMapping(value = "removeuserfromgroup",consumes = MediaType.ALL_VALUE,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity removeuserfromgroup(@RequestBody Requestdata rdata,@AuthenticationPrincipal UserDetails details){
        if( groupChatImpl.getAdminByGroupId(rdata.getGid()).getMobile().equals(details.getUsername())) {
            groupChatImpl.removeuserfromgroup( userImpl.getUserByMobile( Integer.parseInt(rdata.getUid())), Integer.parseInt(rdata.getGid()));
            return new ResponseEntity("done",userImpl.getAllUserInGroup(Integer.parseInt(rdata.getGid().toString().trim())),rdata.getGid());
        }else {
            return new ResponseEntity("you are not authorize for it","");
        }
    }
    
}
