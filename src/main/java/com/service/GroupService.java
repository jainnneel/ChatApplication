package com.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.GroupChat;

@Repository
public interface GroupService extends JpaRepository<GroupChat,Integer> {

    GroupChat findByGroupName(String groupName);

    @Query(value = "select groupchat from GroupChat groupchat JOIN groupchat.entities en WHERE en.id = ?1")
    List<GroupChat> getallgroupbyentity(int entity);

    /*
     * select distinct distributor from Distributor distributor join
     * distributor.towns town join town.district district where district.name =
     * :name
     */
}
