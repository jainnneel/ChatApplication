package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.AddingImpl;
import com.dao.ChastMessageImpl;
import com.dao.GroupChatImpl;
import com.dao.UserImpl;
import com.model.ChatMessage;
import com.model.GroupChat;
import com.model.ResponseEntity;
import com.model.UserAdded;
import com.model.UserEntity;
import com.websocketconfigre.WebSocketSessionListener;

@Controller
public class DashBoardController {

    @Autowired
    UserImpl userImpl;

    @Autowired
    AddingImpl addingImpl;
    
    @Autowired
    ChastMessageImpl chatImpl;
    
    @Autowired
    GroupChatImpl groupChatImpl;

    @RequestMapping(value = "/home")
    public String homePage(Model m, @AuthenticationPrincipal UserDetails details) {
        UserEntity entity = userImpl.getUserByMobile(details.getUsername());
        m.addAttribute("user", entity);
        return "home";
    }

    @RequestMapping(value = "adduser", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addUser(@RequestBody Object mobile, @AuthenticationPrincipal UserDetails details) {
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        UserEntity addedUser = userImpl.getUserByMobile(mobile.toString());
        boolean existOrnot = addingImpl.checkAreadyAddedOrNot(addedByUser, addedUser);
        if (addedUser != null) {
            if (addedByUser == addedUser || existOrnot) {
                return new ResponseEntity("alreadyAdded", null);
            } else {
                addingImpl.createUser(new UserAdded(addedByUser, addedUser));
                return new ResponseEntity("done", null);
            }
        } else {
            return new ResponseEntity("not exists", null);
        }
    }
    
    @RequestMapping(value = "addgroup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addgroup(@RequestBody Object groupName, @AuthenticationPrincipal UserDetails details) {
        UserEntity addedByUser = userImpl.getUserByMobile(details.getUsername());
        GroupChat groupChat = groupChatImpl.getGroupByName(groupName.toString().trim());
        if(groupChat!=null) {
                userImpl.addUserToGroup(addedByUser,groupChat);
                return new ResponseEntity("You Added in Group "+groupName.toString(), null);
        }else {
               return new ResponseEntity("No Group found", null);
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
   
}
