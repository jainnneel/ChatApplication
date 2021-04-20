package com.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.UserDto;
import com.model.GroupChat;
import com.model.UserEntity;
import com.service.UserRepository;

@Service
public class UserImpl{

    @Autowired
    UserRepository userRepo;
    
    @Autowired
    GroupChatImpl groupImpl;
    
    
    public UserEntity getUserByMobile(String mobile) {
        System.out.println("grtting userRepo by its mobile number");
        UserEntity entity=null;
        try {
            entity =userRepo.findByMobile(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
    
    public String addUserToGroup(UserEntity entity, GroupChat groupChat) {
        try {
            List<GroupChat> chats = entity.getGroupChat();
//                    groupImpl.getGroupListByUserEntity(entity);
            if(chats.contains(groupChat)) {
                return "You are already member of group";
            }else {
                System.out.println(groupChat.getEntities());
                chats.add(groupChat);
//                List<UserEntity> entities = getAllUserInGroup(groupChat.getGid());
//                entities.add(entity);
//                groupChat.setEntities(entities);
                entity.setGroupChat(chats);
                userRepo.save(entity);
                    return "done";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public List<UserEntity> getAllUserInGroup(int groupId){
        List<UserEntity> entities = null;
        try {
            entities = userRepo.getAllUserInGroup(groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
    
//    @Cacheable(value = "usersCache" , key = "#p1")
    public UserEntity getUserByMobile(int id) {
        UserEntity entity = null ;
        try {
            entity = userRepo.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
    }

    public UserEntity createuser(UserDto userDto) {
       try {
        UserEntity entity = new UserEntity();
           entity.setMobile(userDto.getMobile());
           entity.setName(userDto.getUsername());
           entity.setPass(userDto.getPass());
           entity.setEnable(false);
           return  userRepo.save(entity);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       return null;
   }

    public void updatelastseen(String mobile) {
        UserEntity entity =  getUserByMobile(mobile);
        entity.setLastseen(LocalDateTime.of(LocalDate.now(), LocalTime.now()).toString());
        userRepo.save(entity);
    }

    public void updateToken(String username, String token1) {
        UserEntity entity =  getUserByMobile(username);
        entity.setWebpushToken(token1);
        userRepo.save(entity);
    }
    

//    public void addingUserListToGroup(List<UserEntity> entities, GroupChat groupchat) {
//        try {
//            for(UserEntity entity:entities) {
//                addUserToGroup(entity,groupchat);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
