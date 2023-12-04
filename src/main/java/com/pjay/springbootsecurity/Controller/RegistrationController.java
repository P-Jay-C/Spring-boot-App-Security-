package com.pjay.springbootsecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Service.UserService;
import com.pjay.springbootsecurity.event.RegistrationCompleteEvent;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;
    
    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequestDto userRequestDto){
        User user = userService.registerUser(userRequestDto);
        publisher.publishEvent(
            new RegistrationCompleteEvent(
                user, "url"
            ));
        return "Success";
    }
}
