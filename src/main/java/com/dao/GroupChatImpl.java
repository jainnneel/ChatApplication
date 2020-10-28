package com.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "usersCache" , key = "#p3")
    public GroupChat getGroupByName(String groupName) {
        System.out.println("getting group by name");
        GroupChat groupChat = null;
        try {
            groupChat = groupService.findByGroupName(groupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupChat;
    }

    public List<GroupChat> getGroupListByUserEntity(UserEntity entity) {
        List<GroupChat> chats = null;
        try {
            chats = groupService.getallgroupbyentity(entity.getId());
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

            for (UserEntity entity : allAddedUser) {
                if (!allUserInGroup.contains(entity)) {
                    entities.add(entity);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entities;
    }

    public HashSet<GroupChat> getGroupsOfUserAdminAndPublic(Object mobile) {
        UserEntity userEntity = userImpl.getUserByMobile(mobile.toString());
        HashSet<GroupChat> list = new HashSet<GroupChat>();
        try {
            list = groupService.getGroupsOfUserAdminAndPublic(userEntity.getId(), "private", userEntity, "public");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public void removeuserfromgroup(UserEntity entity, int gid) {
        try {
            if (groupService.findById(gid).isPresent()) {
                GroupChat chat = groupService.findById(gid).get();
                if (chat.getAdmin().equals(entity)) {
                    groupService.delete(chat);
                } else {
                    chat.getEntities().remove(entity);
                    groupService.save(chat);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String addUserToGroup(UserEntity entity, GroupChat groupChat) {
        try {
            List<GroupChat> chats = entity.getGroupChat();
//                    groupImpl.getGroupListByUserEntity(entity);
            if (chats.contains(groupChat)) {
                return "You are already member of group";
            } else {
//                System.out.println(groupChat.getEntities());
//                chats.add(groupChat);
                List<UserEntity> entities = groupChat.getEntities();
                // getAllUserInGroup(groupChat.getGid());
                entities.add(entity);
                groupChat.setEntities(entities);
//                entity.setGroupChat(chats);
                groupService.save(groupChat);
                return "done";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Cacheable(value = "usersCache" , key = "#p2")
    public UserEntity getAdminByGroupId(String gid) {
        System.out.println("getting group by id");
        UserEntity entity = null;
        try {
            entity = groupService.getAdminByGroupId(Integer.parseInt(gid.trim()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } 
        return entity;
    }

}
