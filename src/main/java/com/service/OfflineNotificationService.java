package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.OfflineNotifiacation;

@Repository
public interface OfflineNotificationService extends JpaRepository<OfflineNotifiacation, Integer> {

    @Query("delete from OfflineNotifiacation where fromMobile=?1 and tofromMobile=?2")
    @Modifying
    @Transactional
    void deleteAddingRequest(String from, String to);

    @Query("from OfflineNotifiacation where tofromMobile=?1")
    List<OfflineNotifiacation> getallNotificationofuser(String mobile);

    List<OfflineNotifiacation> findByTofromMobile(String mobile);

}
