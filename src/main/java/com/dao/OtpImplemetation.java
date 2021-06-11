package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.OneTimePassword;
import com.model.UserEntity;
import com.service.OtpRepo;
import com.service.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class OtpImplemetation {

    @Autowired
    private OtpRepo otpRepo;
    
    @Autowired
    private UserRepository userRepository;

    public String sendOtp(UserEntity entity) {
        try {
            OneTimePassword getotpbyuser = getotpbyuser(entity);
            if(getotpbyuser!=null) otpRepo.delete(getotpbyuser);
            OneTimePassword oneTimePassword  = new OneTimePassword(entity);
            System.out.println(oneTimePassword.getOtpValue());
            Message message = Message.creator(
                    new PhoneNumber("+91"+entity.getMobile()),
                    new PhoneNumber("+15103909234"),
                    "Verification Code :" + oneTimePassword.getOtpValue())
                    .create();
            System.out.println(message.getStatus());
            otpRepo.save(oneTimePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resendOtp(UserEntity userEntity) {
        OneTimePassword getotpbyuser = getotpbyuser(userEntity);
        if(getotpbyuser!=null) otpRepo.delete(getotpbyuser);
        OneTimePassword oneTimePassword  = new OneTimePassword(userEntity);
        System.out.println(oneTimePassword.getOtpValue());
        Message message = Message.creator(
                new PhoneNumber("+91"+userEntity.getMobile()),
                new PhoneNumber("+15103909234"),
                "Verification Code :" + oneTimePassword.getOtpValue())
                .create();
        System.out.println(message.getStatus());
        otpRepo.save(oneTimePassword);
    }
    
    public OneTimePassword getotpbyuser(UserEntity entity) {
        try {
            return otpRepo.getbyuser(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifyOtp(String otpDto) {
        try {
            OneTimePassword oneTimePassword = otpRepo.getByOtp(otpDto);
            
            if (oneTimePassword!=null) {
                UserEntity userEntity = oneTimePassword.getUserEntity();
                if (userEntity!=null) {
                    userEntity.setEnable(true);
                    Message message = Message.creator(
                            new PhoneNumber("+91"+userEntity.getMobile()),
                            new PhoneNumber("+15103909234"),
                            "Welcome you successfully registered")
                            .create();
                    System.out.println(message.getStatus());
                    userRepository.save(userEntity);
                    otpRepo.delete(oneTimePassword);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
}


}





