package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MessagingSystem.pushnotification.FCMService;
import com.MessagingSystem.pushnotification.PushNotificationRequest;
import com.dao.OtpImplemetation;
import com.dao.UserImpl;
import com.dto.OtpDto;
import com.dto.UserDto;
import com.exception.UserExistException;
import com.model.ResponseEntity;
import com.model.UserEntity;
import com.security.Jwtutil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegController {

    @Autowired 
    UserImpl userimpl;
    
    @Autowired
    OtpImplemetation otpimpl;
    
    @Autowired
    private Jwtutil jwtutil;
    
    @Autowired
    FCMService fcmService;
    
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  registeruser(@RequestBody UserDto userDto,BindingResult results) {
        
        ResponseEntity responseEntity = new ResponseEntity();
        try {
            
            ValidationUtils.rejectIfEmptyOrWhitespace(results, "username", "Name can not be empty.");
            ValidationUtils.rejectIfEmptyOrWhitespace(results, "mobile", "email can not be empty.");
            ValidationUtils.rejectIfEmptyOrWhitespace(results, "pass", "password can not be empty.");

            if (results.hasErrors()) {
                responseEntity.setData(results.getAllErrors());
                responseEntity.setStatus("invalid data");
                responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
                return responseEntity;
            }
            
            userDto.setMobile(userDto.getMobile().toLowerCase());
            
             UserEntity userByMobile = userimpl.getUserByMobile(userDto.getMobile());
             
             if (userByMobile!=null) {
                if (userByMobile.isEnable()) {
                    responseEntity.setException(new UserExistException("user already exist"));
                    responseEntity.setHttpStatus(HttpStatus.FOUND);
                    responseEntity.setData("user already exist");
                    return responseEntity;
                }else {
                    otpimpl.resendOtp(userByMobile);
                    responseEntity.setData("user mobile not verified !! otp send plz verify");
                    return responseEntity;
                } 
            }
             
            UserEntity entity = userimpl.createuser(userDto);  
            
            otpimpl.sendOtp(entity);
            responseEntity.setHttpStatus(HttpStatus.OK);
            responseEntity.setData(entity.getId());
            responseEntity.setStatus("otp send");
            
        } catch (Exception e) {
            responseEntity.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return responseEntity;
    }
    
    
    @PostMapping(value = "otpverify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity otpverify(@RequestBody OtpDto otpDto, BindingResult results) {

        ResponseEntity responseEntity = new ResponseEntity();
        ValidationUtils.rejectIfEmptyOrWhitespace(results, "otpDto", "otp can not be empty.");

        if (results.hasErrors()) {
            responseEntity.setData(results.getAllErrors());
            responseEntity.setStatus("invalid data");
            responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }

        if (otpimpl.verifyOtp(otpDto.getOtpDto())) {
            responseEntity.setData("otp successfully verify");
            responseEntity.setHttpStatus(HttpStatus.OK);
            return responseEntity;
        } else {
            responseEntity.setData("Otp is incorrect");
            responseEntity.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
            return responseEntity;
        }

    }
    
    
    @GetMapping("/loginsuccess")
    @CrossOrigin
    public ResponseEntity loginsuccess(@RequestParam("username") String  username) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus("success");
        UserEntity userId = userimpl.getUserByMobile(username);
        responseEntity.setData(userId);
        responseEntity.setHttpStatus(HttpStatus.OK);
        responseEntity.setToken(jwtutil.generateToken(username));
        return responseEntity;
    }
    @GetMapping("/logout")
    @CrossOrigin
    public ResponseEntity logout() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setData("logoutSuccess");
        responseEntity.setHttpStatus(HttpStatus.OK);
        
        responseEntity.setStatus("success");
        return responseEntity;
    }
    
    @GetMapping("/login")
    @CrossOrigin
    public ResponseEntity loginpage() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setData("Enter token for access");
        responseEntity.setHttpStatus(HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }
    
    @GetMapping("/loginattemtfailed")
    @CrossOrigin
    public ResponseEntity loginpage1() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setData("Wrong username or password");
        responseEntity.setHttpStatus(HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }
    
    
    @PostMapping(value =  "/noti",consumes = MediaType.ALL_VALUE )
    @CrossOrigin
    public String loginpage22(@RequestBody String tokeString , @AuthenticationPrincipal UserDetails userDetails) {
//        String token = "cLstNBsN35L803S_KORdVq:APA91bFyFLeCpQ7q5Y0BwS0_2QmCwUWS6KfilRtH-GnEtSI-4EVdFK_d3ehPvuLYvULyoxRjqW9Uoz_G90hXStWxOZK4CDscZEgLlITDRR8PR4YP58-kuamU4IuCt5NV8oPzENN71uM5";
       String token1 = tokeString.substring(3,tokeString.length()-4);
//       System.out.println(token1);
//        System.out.println(token);
        token1 = token1.replace("%3A",":");
//        System.out.println(token1);
//        System.out.println(token.equals(token1));
        token1 = tokeString.substring(1,tokeString.length()-1);
        userimpl.updateToken(userDetails.getUsername(),token1);
//        
//        
//        PushNotificationRequest pushNotificationRequest=new PushNotificationRequest();
//        pushNotificationRequest.setMessage("Send push notifications from Spring Boot server");
//        pushNotificationRequest.setTitle("test Push Notification");
//        pushNotificationRequest.setToken(token1);
//        Map<String, String> appData= new HashMap<>();
//            appData.put("name", "PushNotification");
//        try {
//            fcmService.sendMessage(appData, pushNotificationRequest);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }   
//        
        
       return "done";
    }
    
    
//    @RequestMapping(value = "/login")
//    public String loginPage() {
//        return "login";
//    }
//    
}
