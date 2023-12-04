package com.pjay.springbootsecurity.event.Listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Service.UserService;
import com.pjay.springbootsecurity.event.RegistrationCompleteEvent;

public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // TODO Auto-generated method stub
        // Create the verification Token for the User with link

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send mail to user
    }    
}
