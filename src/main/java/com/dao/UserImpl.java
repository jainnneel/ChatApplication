package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.GroupChat;
import com.model.UserEntity;
import com.service.UserRepository;

@Service
public class UserImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;
    
    @Autowired
    GroupChatImpl groupImpl;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity =  getUserByMobile(username);
        return new User(entity.getMobile(), entity.getPass(), entity.isEnable(), true, true, true, new ArrayList<>());
    }
    
    @Cacheable(value = "usersCache" , key = "#p0")
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
    
    @Cacheable(value = "usersCache" , key = "#p1")
    public UserEntity getUserByMobile(int id) {
        System.out.println("getting user by its id");
        UserEntity entity = null ;
        try {
            entity = userRepo.findById(id).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
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
