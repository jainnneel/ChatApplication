package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.model.UserAdded;
import com.model.UserEntity;
import com.service.AddingService;

@Service
public class AddingImpl {

    @Autowired
    AddingService addingrepo;
    
    @Autowired
    UserImpl userImpl;
    
    public void createUser(UserAdded added) {
        try {
            addingrepo.save(added);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkAreadyAddedOrNot(UserEntity addedByUser, UserEntity addedUser) {
        boolean res = false;
        
        try {
            UserAdded  useradd=  addingrepo.checkAreadyAddedOrNot(addedByUser,addedUser);
            if(useradd!=null) {
                res=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return res;
    }
    
    public List<UserEntity> getAllUserAddedByUser(String mobile) {
        System.out.println("jain neel cache not   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1  done ");
        List<UserEntity> entities=null;
        try {
            entities = addingrepo.getAllUserAddedByUser(userImpl.getUserByMobile(mobile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
    
    public Object removeuserfromdata(UserEntity addedByUser, UserEntity addedUser) {
        addingrepo.deleteusermapping(addedByUser,addedUser);
        addingrepo.deleteusermapping(addedUser,addedByUser);
        return "done";
    }
}
