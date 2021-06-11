package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dao.OtpImplemetation;
import com.dao.UserImpl;
import com.model.UserEntity;
import com.service.LoginAttemptService;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private LoginAttemptService loginAttemptService;
    
    @Autowired 
    UserImpl userimpl;
    
    @Autowired
    private OtpImplemetation otpService;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        loginAttemptService.loginFailed(getClientIP(request));
        
        if (request.getAttribute("userMobile")!=null) {
            UserEntity entity =   userimpl.getUserByMobile((String)request.getAttribute("userMobile"));
            otpService.resendOtp(entity);
            setDefaultFailureUrl("/loginattemtfailed1");
       }else {
           setDefaultFailureUrl("/loginattemtfailed");
           
           String errorMessage = "incorrect details";
           
           if (exception.getMessage()
                   .equalsIgnoreCase("blocked")) {
                       errorMessage = "user blocked";
           }   
           HttpSession session = request.getSession(false);
           System.out.println(request);
           if (session!=null) {
               session
               .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage); 
           }
       }
        super.onAuthenticationFailure(request, response, exception);
    }
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
