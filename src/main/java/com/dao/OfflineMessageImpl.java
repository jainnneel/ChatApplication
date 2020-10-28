package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.OfflineMessage;
import com.service.OfflineMessageService;

@Service
public class OfflineMessageImpl {

    @Autowired
    OfflineMessageService messageService;
    
    public void createMessage(OfflineMessage message) {
        try {
            messageService.save(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllUserInformation(String mobile) {
        List<String> mobileList = null;
        try {
            mobileList = messageService.findByToMobile(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileList;
    }

    public void deleteOfflineMessage(String fromMobile, String toMobile) {
        messageService.deleteOfflineMessage(fromMobile,toMobile);
    }

    public void deleteOfflineGroupMessage(String fromMobile, String toMobile) {
        messageService.deleteOfflineGroupMessage(fromMobile,toMobile);
    }
}
