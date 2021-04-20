package com.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.OneTimePassword;
import com.model.UserEntity;

@Repository
public interface OtpRepo  extends JpaRepository<OneTimePassword, Long> {

    @Query(value = "from OneTimePassword where userEntity=?1")
    OneTimePassword getbyuser(UserEntity entity);

    @Query(value = "from OneTimePassword where otpValue=?1")
    OneTimePassword getByOtp(String otpDto);

}
