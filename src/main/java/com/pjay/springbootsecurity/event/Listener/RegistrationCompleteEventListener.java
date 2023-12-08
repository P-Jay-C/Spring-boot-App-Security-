package com.pjay.springbootsecurity.event.Listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Service.UserService;
import com.pjay.springbootsecurity.event.RegistrationCompleteEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
      
        // Create the verification Token for the User with link

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send mail to user
        String url = event.getApplicationUrl()+"api/v1/auth/verifyRegistration?token=" + token;

        log.info("Click the link to verify your account: {}", url);
    }    
}
