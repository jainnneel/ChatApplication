package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public UserEntity getUserByMobile(String mobile) {
        UserEntity entity=null;
        try {
            entity =userRepo.findByMobile(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
    
    public void addUserToGroup(UserEntity entity, GroupChat groupChat) {
        try {
            List<GroupChat> chats = groupImpl.getGroupListByUserEntity(entity);
            chats.add(groupChat);
            List<UserEntity> entities = getAllUserInGroup(groupChat.getGid());
            entities.add(entity);
            groupChat.setEntities(entities);
            entity.setGroupChat(chats);
            userRepo.save(entity);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
}