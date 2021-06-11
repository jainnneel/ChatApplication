package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dao.UserImpl;


@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    
    @Autowired 
    UserImpl userimpl;
    
    @Autowired
    private Jwtutil jwtutil;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        setDefaultTargetUrl("/loginsuccess?username="+authentication.getName());
//        loginNotification(authentication,request);
        Cookie cookie = new Cookie("JWT-Token", jwtutil.generateToken(authentication.getName()));
        cookie.setSecure(false);
        cookie.setPath("");
        cookie.setDomain("");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        super.onAuthenticationSuccess(request, response, authentication);
    }
//    private void loginNotification(Authentication authentication, HttpServletRequest request) {
//        try {
//            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//            UserEntity ud = userimpl.getUserByMobile(userDetails.getUsername());
//            System.out.println(ud); 
//            if (authentication.getPrincipal() != null) {
//                deviceService.verifyDevice(ud,request);
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
