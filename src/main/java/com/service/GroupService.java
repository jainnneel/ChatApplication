package com.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.GroupChat;
import com.model.UserEntity;

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
    
    @Query(value = "select groupchat from GroupChat groupchat JOIN groupchat.entities en WHERE (en.id = ?1 AND groupchat.typeOfGroup =?4) OR (groupchat.typeOfGroup =?2 AND groupchat.admin =?3)")
    HashSet<GroupChat> getGroupsOfUserAdminAndPublic(int entityid,String type,UserEntity entity,String type1);

    @Query(value = "select admin from GroupChat groupchat WHERE groupchat.gid = ?1")
    UserEntity getAdminByGroupId(int parseInt);
}
