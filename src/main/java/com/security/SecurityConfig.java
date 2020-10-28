package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dao.UserImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
   
    
    @Autowired 
    UserImpl userimpl;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http 
             .authorizeRequests()
             .antMatchers("/login").permitAll()
             .antMatchers("/home").authenticated()
             .and()
             .csrf().disable()
             .formLogin()
               .loginPage("/login").permitAll()
               .loginProcessingUrl("/login")
               .defaultSuccessUrl("/home")
               .and()
               .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .clearAuthentication(true)
            .logoutRequestMatcher( new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").permitAll()
        .and()
            .rememberMe()
        .and()
            .sessionManagement()                          
            .maximumSessions(1)                         
            .maxSessionsPreventsLogin(false)          
            .expiredUrl("/login?expired");  
            
        
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()); 
    }
    
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider d = new  DaoAuthenticationProvider();
        d.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        d.setUserDetailsService(userimpl);
        return d;
    }

}
