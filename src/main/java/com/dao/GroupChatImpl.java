package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.GroupChat;
import com.model.UserEntity;
import com.service.GroupService;

@Service
public class GroupChatImpl {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserImpl userImpl;
    
    @Autowired
    private AddingImpl userAdded;
    
    public GroupChat getGroupByName(String groupName) {
        GroupChat groupChat = null;
        try {
            groupChat = groupService.findByGroupName(groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupChat;
    }
    
    public List<GroupChat> getGroupListByUserEntity(UserEntity entity){
        List<GroupChat> chats = null;
       try {
        chats =  groupService.getallgroupbyentity(entity.getId());
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       return chats;
    }

    public void createGroup(GroupChat group) {
        try {
            groupService.save(group);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<UserEntity> getNotAddedMembers(GroupChat groupChat, String mobile) {
        List<UserEntity> entities = new ArrayList<>();
        
         try {
            List<UserEntity> allUserInGroup = userImpl.getAllUserInGroup(groupChat.getGid());
             List<UserEntity> allAddedUser = userAdded.getAllUserAddedByUser(mobile.trim());
             
             for(UserEntity entity:allAddedUser) {
                 if(!allUserInGroup.contains(entity)) {
                     entities.add(entity);
                 }
             }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entities;
    }

    public List<GroupChat> getGroupsOfUserAdminAndPublic(Object mobile) {
        UserEntity userEntity = userImpl.getUserByMobile(mobile.toString());
        List<GroupChat> list = new ArrayList<GroupChat>();
        try {
             list = groupService.getGroupsOfUserAdminAndPublic(userEntity.getId(),"private",userEntity,"public");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return list;
    }
  
}
