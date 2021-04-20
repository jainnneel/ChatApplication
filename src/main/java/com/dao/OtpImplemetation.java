package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.OneTimePassword;
import com.model.UserEntity;
import com.service.OtpRepo;
import com.service.UserRepository;

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
            otpRepo.save(oneTimePassword);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void resendOtp(UserEntity userEntity) {
        OneTimePassword getotpbyuser = getotpbyuser(userEntity);
        if(getotpbyuser!=null) otpRepo.delete(getotpbyuser);
        OneTimePassword oneTimePassword  = new OneTimePassword(userEntity);
        System.out.println(oneTimePassword.getOtpValue());
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





