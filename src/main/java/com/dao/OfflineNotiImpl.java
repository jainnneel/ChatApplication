package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.OfflineNotifiacation;
import com.service.OfflineNotificationService;

@Service
public class OfflineNotiImpl {

    @Autowired
    OfflineNotificationService notificationService;

    public void createOfflineMessage(OfflineNotifiacation offlineNotifiacation) {
        try {
            notificationService.save(offlineNotifiacation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNotificationForUser(String from, String to) {
        try {
            notificationService.deleteAddingRequest(from,to);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<OfflineNotifiacation> getallNotificationofuser(String mobile) {
        List<OfflineNotifiacation> notifiacations = null;
        try {
            notifiacations =  notificationService.getallNotificationofuser(mobile.trim());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return notifiacations;
    }

    public boolean getnotification(String mobile) {
        List<OfflineNotifiacation> list=null;  
        boolean noti=false;
        try {
            list =  notificationService.findByTofromMobile(mobile,false);
            if(list.size()>0) {
                noti=true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noti;
    }

    public void deletenotification(int nid) {
        try {
            notificationService.deleteById(nid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setAllNotiOfUserSeen(String fromMobile) {
        notificationService.setAllNotiOfUserSeen(true,fromMobile);
    }
    
    
}
