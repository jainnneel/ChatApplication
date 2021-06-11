package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ChastMessageImpl;
import com.dao.StockGroupImpl;
import com.dao.UserImpl;
import com.dto.GroupRequestDto;
import com.dto.JoinGroupRequst;
import com.dto.JoinGroupResponse;
import com.dto.PaymentRequest;
import com.model.ChatMessage;
import com.model.MessageModel;
import com.model.ResponseEntity;
import com.model.StockMarketGroup;
import com.model.UserEntity;
import com.model.ValidationModel;

@RestController
@RequestMapping("/stock")
public class GroupController {

    @Autowired
    private StockGroupImpl stockMarketGroup;
    
    @Autowired
    private ChastMessageImpl chatImpl;
    
    @Autowired
    private UserImpl userImpl;
    
    @PostMapping(value = "group",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createGroup(@RequestBody GroupRequestDto reqDto,BindingResult results,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "groupName", "group name can not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "freeOrNot", "group name can not be empty");
        if (results.hasErrors()) {
            responseEntity.setStatus("failed");
            responseEntity.setData(results.getAllErrors());
            responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }
        StockMarketGroup group = stockMarketGroup.createGroup(reqDto,details);
        if (group!=null) {
            responseEntity.setStatus("success");
            responseEntity.setData(group);
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("something went wrong");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @PostMapping(value = "joingroup",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity joinGroup(@RequestBody JoinGroupRequst joinreq,BindingResult results,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "groupId", "groupid is required");
        if (results.hasErrors()) {
            responseEntity.setStatus("failed");
            responseEntity.setData(results.getAllErrors());
            responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }
        JoinGroupResponse join = stockMarketGroup.addUserToGroup(joinreq,details);
        if (join!=null) {
            responseEntity.setStatus("success");
            responseEntity.setData(join);
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("something went wrong");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntity;
    }
    
    @PostMapping(value = "payment",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity completePayment(@RequestBody PaymentRequest paymetreq,BindingResult results,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "orderId", "orderId is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "paymentId", "paymentId is required");
        
        if (results.hasErrors()) {
            responseEntity.setStatus("failed");
            responseEntity.setData(results.getAllErrors());
            responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }
        
        if (stockMarketGroup.verifyAndUserToGroup(paymetreq,details)) {
            responseEntity.setStatus("success");
            responseEntity.setData("added");
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("something went wrong");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntity;
    }
    
    @GetMapping(value = "getgroupmember/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGroupmembers(@PathVariable("groupId") String groupId) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<UserEntity> entities =  stockMarketGroup.getAllGroupMembers(groupId);
        if (entities!=null) {
            responseEntity.setStatus("success");
            responseEntity.setData(entities);
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("something went wrong");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return responseEntity;
    }
    
    @GetMapping(value = "deletegroup/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteRequest(@PathVariable("groupId") String groupId,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (stockMarketGroup.deleteGroup(groupId,details)) {
            responseEntity.setStatus("success");
            responseEntity.setData("done");
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("can't delete");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @GetMapping(value = "leavegroup/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity leaveFromGroup(@PathVariable("groupId") String groupId,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (stockMarketGroup.leaveGroup(groupId,details)) {
            responseEntity.setStatus("success");
            responseEntity.setData("done");
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("can't delete");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @GetMapping(value = "getuserstockgroups",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAlluserAddedGroup(@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (details!=null) {
            List<StockMarketGroup> allUserGroup = stockMarketGroup.getAllUserGroup(details);
            responseEntity.setStatus("success");
            responseEntity.setData(allUserGroup);
            responseEntity.setHttpStatus(HttpStatus.OK);
        }else {
            responseEntity.setStatus("failed");
            responseEntity.setData("can't delete");
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @GetMapping(value = "groupchats/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGroupChats(@PathVariable("groupId") String groupId,@AuthenticationPrincipal UserDetails details) {
        ResponseEntity responseEntity = new ResponseEntity();
        if(details!=null) {
            UserEntity entity = userImpl.getUserByMobile(details.getUsername());
            StockMarketGroup group = stockMarketGroup.getGroupById(groupId);
            if(group.getGroupMembers().contains(entity)) {
                List<ChatMessage> chatMessages =  chatImpl.getAllMessageForGroup(groupId);
                responseEntity.setData(getMessageModel(chatMessages));
                responseEntity.setHttpStatus(HttpStatus.OK);
            }
        }
        return responseEntity;
    }
    
    private List<MessageModel> getMessageModel(List<ChatMessage> chatMessages) {
        List<MessageModel> list= new ArrayList<>();
        chatMessages.forEach(chat -> {
            MessageModel messageModel = new MessageModel();
            messageModel.setChatId(chat.getId());
            messageModel.setFromLogin(chat.getFromMobile());
            messageModel.setToUser(chat.getToMobile());
            messageModel.setSeenOrNot(chat.getSeenOrNot());
            messageModel.setMessage(chat.getMessage());
            messageModel.setDate(chat.getDate());
            messageModel.setToGroup(chat.getFromMobile());
            list.add(messageModel);
        });
        return list;
    }
    
    @GetMapping(value = "isusermember/{groupid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity isUsergroupmember(@PathVariable("groupid") String groupid,@AuthenticationPrincipal UserDetails details){
        ResponseEntity entity = new ResponseEntity();
        ValidationModel userMember = stockMarketGroup.isUserMember(groupid,details);
        if(userMember!=null) {
            entity.setData(userMember);
            entity.setHttpStatus(HttpStatus.OK);
            entity.setStatus("success");
        }else {
            entity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            entity.setStatus("failed");
        }
        return entity;
    }
}


























