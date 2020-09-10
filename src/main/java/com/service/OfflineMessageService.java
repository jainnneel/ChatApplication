package com.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.OfflineMessage;

@Repository
public interface OfflineMessageService extends JpaRepository<OfflineMessage, Integer>{

    @Query("select fromMobile from OfflineMessage where toMobile=?1")
    List<String> findByToMobile(String mobile);

    @Transactional
    @Modifying
    @Query("delete from OfflineMessage where fromMobile=?1 AND toMobile=?2")
    void deleteOfflineMessage(String fromMobile, String toMobile);

    @Transactional
    @Modifying
    @Query("delete from OfflineMessage where fromMobile=?1 AND toMobile=?2")
    void deleteOfflineGroupMessage(String fromMobile, String toMobile);

}
