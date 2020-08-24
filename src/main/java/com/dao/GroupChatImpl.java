package com.dao;

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
  
}
