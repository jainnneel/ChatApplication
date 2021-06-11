package com.dao;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dto.GroupRequestDto;
import com.dto.JoinGroupRequst;
import com.dto.JoinGroupResponse;
import com.dto.PaymentRequest;
import com.model.GroupBooking;
import com.model.GroupChatMessages;
import com.model.StockMarketGroup;
import com.model.UserEntity;
import com.model.ValidationModel;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.service.GroupBookingService;
import com.service.GroupChatMessageRepo;
import com.service.StockGroupChat;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class StockGroupImpl  {

    @Autowired
    private StockGroupChat stockGroupRepo;
    
    @Autowired
    private UserImpl userImpl;
    
    @Autowired
    private GroupBookingService groupBookingService;
    
    @Autowired
    private GroupChatMessageRepo chatMessageRepo; 
    
    public StockMarketGroup createGroup(GroupRequestDto reqDto,UserDetails details) {
        try {
            UserEntity entity = userImpl.getUserByMobile(details.getUsername());
            List<UserEntity> entities = new ArrayList<>();
            entities.add(entity);
            StockMarketGroup group = new StockMarketGroup();
            if (reqDto.isFreeOrNot()) {
                group.setGroupId(LocalTime.now().getNano()+"");
                group.setAdminUser(entity);
                group.setFreeOrNot(true);
                group.setGroupName(reqDto.getGroupName());
                group.setDateOfCreation(Date.valueOf(LocalDate.now()));
                group.setGroupMembers(entities);
                if(reqDto.getDesc()!=null)  group.setGroupDescription(reqDto.getDesc()); else group.setGroupDescription("");
                group.setPrice("0");
            }else {
                if(reqDto.getPrice() != null) {
                    group.setGroupId(LocalTime.now().getNano()+"");
                    group.setAdminUser(userImpl.getUserByMobile(details.getUsername()));
                    group.setFreeOrNot(false);
                    group.setGroupName(reqDto.getGroupName());
                    group.setDateOfCreation(Date.valueOf(LocalDate.now()));
                    group.setGroupMembers(entities);
                    if(reqDto.getDesc()!=null)  group.setGroupDescription(reqDto.getDesc()); else group.setGroupDescription("");
                    group.setPrice(reqDto.getPrice());
                }else {
                    return null;
                }
            }
            stockGroupRepo.save(group);
            return group;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JoinGroupResponse addUserToGroup(JoinGroupRequst joinreq, UserDetails details) {
       JoinGroupResponse groupResponse = new JoinGroupResponse();
        try {
            StockMarketGroup stockMarketGroup = getGroupById(joinreq.getGroupId());
            if(stockMarketGroup!=null) {
                
                if (stockMarketGroup.isFreeOrNot()) {
                    UserEntity userByMobile = userImpl.getUserByMobile(details.getUsername());
                    if (userByMobile!=null) {
                        List<UserEntity> groupMembers = stockMarketGroup.getGroupMembers();
                        groupMembers.add(userByMobile);
                        stockMarketGroup.setGroupMembers(groupMembers);
                        stockGroupRepo.save(stockMarketGroup);
                        return groupResponse;
                    }
                }else {
                    UserEntity userByMobile = userImpl.getUserByMobile(details.getUsername());
                    if (userByMobile!=null) {
                        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_9B8AXwdinhrVTx", "aeRfMEbWIgA3mA2fOsY7LU0G");
                        JSONObject options = new JSONObject();
                        options.put("amount", Integer.parseInt(stockMarketGroup.getPrice())*100);
                        options.put("currency", "INR");
                        Order order = razorpayClient.Orders.create(options);
                        GroupBooking booking = new GroupBooking();
                        booking.setEntity(userByMobile);
                        booking.setOrderId(order.get("id"));
                        booking.setStatus("Not paid");
                        booking.setGroupId(stockMarketGroup.getGroupId());
                        groupBookingService.save(booking);
                        groupResponse.setOrderId(order.get("id"));
                        return groupResponse;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public StockMarketGroup getGroupById(String groupId) {
        Optional<StockMarketGroup> group = stockGroupRepo.findById(groupId);
        if (group.isPresent()) {
            return group.get();
        }
        return null;
    }

    public boolean verifyAndUserToGroup(PaymentRequest paymetreq, UserDetails details) {
        try {
            UserEntity userByMobile = userImpl.getUserByMobile(details.getUsername());
            if (userByMobile!=null) {
                GroupBooking booking = groupBookingService.getBookingByOrderId(paymetreq.getOrderId());
                if(booking!=null) {
                    StockMarketGroup stockMarketGroup = getGroupById(booking.getGroupId());
                    RazorpayClient razorpayClient = new RazorpayClient("rzp_test_9B8AXwdinhrVTx", "aeRfMEbWIgA3mA2fOsY7LU0G");
                    Payment payment = razorpayClient.Payments.fetch(paymetreq.getPaymentId());
                    System.out.println(payment.get("amount").toString() +" " + stockMarketGroup.getPrice() +" " + payment.get("order_id").toString() +" "+paymetreq.getOrderId() +"  "+payment.get("status").toString().trim().equals("captured"));
                    if (payment!=null && payment.get("amount").toString().trim().equals(Integer.parseInt(stockMarketGroup.getPrice())*100+"") && payment.get("order_id").toString().trim().equals(paymetreq.getOrderId()) && payment.get("status").toString().trim().equals("captured")){
                        booking.setPaymentId(paymetreq.getPaymentId());
                        booking.setStatus("paid");
                        booking.setDateOfPayment(Date.valueOf(LocalDate.now()));
                        booking.setTimeOfPayment(Time.valueOf(LocalTime.now()));
                        List<UserEntity> groupMembers = stockMarketGroup.getGroupMembers();
                        groupMembers.add(userByMobile);
                        stockMarketGroup.setGroupMembers(groupMembers);
                        stockGroupRepo.save(stockMarketGroup);
                        String msg = "Congratulation you are now member of group "+stockMarketGroup.getGroupName()+". enjoy daily stocks updates."
                                + " Admin Number : " + stockMarketGroup.getAdminUser().getMobile()
                                + " Admin Name : " + stockMarketGroup.getAdminUser().getName()
                                + " Amount Paid :" + payment.get("amount").toString()
                                + " Payment-Id :" + paymetreq.getPaymentId();
                        Message message = Message.creator(
                                new PhoneNumber("+91"+userByMobile.getMobile()),
                                new PhoneNumber("+15103909234"),
                                msg).create();
                        System.out.println(message.getStatus());
                        groupBookingService.save(booking);
                        return true;
                    }
                }
            }
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<UserEntity> getAllGroupMembers(String groupId) {
            
        StockMarketGroup groupById = getGroupById(groupId);
        if (groupById!=null) {
            return groupById.getGroupMembers();
        }    
        
        return null;
    }

    public boolean deleteGroup(String groupId,UserDetails details) {
        StockMarketGroup groupById = getGroupById(groupId);
        if (groupById!=null && groupById.getAdminUser().getMobile().equals(details.getUsername())) {
            if (!groupById.isFreeOrNot() && groupById.getGroupMembers().size() == 1) {
                stockGroupRepo.deleteById(groupById.getGroupId());
            }else if (groupById.isFreeOrNot()) {
                stockGroupRepo.deleteById(groupById.getGroupId());
            }
            return true;
        }
        
        return false;
    }

    public boolean leaveGroup(String groupId, UserDetails details) {
        StockMarketGroup groupById = getGroupById(groupId);
        UserEntity entity = userImpl.getUserByMobile(details.getUsername());
        if (entity!=null && groupById!=null && !groupById.getAdminUser().getMobile().equals(details.getUsername())) {
            List<UserEntity> groupMembers = groupById.getGroupMembers();
            if(groupMembers.contains(entity)) {
                groupMembers.remove((Object)entity);
            }
            groupById.setGroupMembers(groupMembers);
            stockGroupRepo.save(groupById);
            return true;
        }
        return false;
    }

    public List<StockMarketGroup> getAllUserGroup(UserDetails details) {
        UserEntity entity = userImpl.getUserByMobile(details.getUsername());
        if (entity!=null) {
            return entity.getStockGroups();
        }
        return new ArrayList<>();
        
    }

    public List<GroupChatMessages> getGropuMesageById(String groupId, UserDetails details) {
        try {
            if(details!=null) {
                UserEntity entity = userImpl.getUserByMobile(details.getUsername());
                StockMarketGroup group = getGroupById(groupId);
                if(group.getGroupMembers().contains(entity)) {
                   List<GroupChatMessages> chatMessages= chatMessageRepo.getAllChatsByGroup(group.getGroupId());
                   return chatMessages;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public ValidationModel isUserMember(String groupid, UserDetails details) {
        ValidationModel valid = new ValidationModel();
        boolean[] arr;
        try {
            arr = new boolean[2];
            StockMarketGroup group = getGroupById(groupid);
            if (group!=null) {
                if (group.getGroupMembers().contains(userImpl.getUserByMobile(details.getUsername()))) {
                    arr[1] = true;
                }
            }else {
                arr[0] = true;
            }
            valid.setArr(arr);
            valid.setGroup(group);
            return valid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



















