package com.pjay.springbootsecurity.Service;

import org.springframework.stereotype.Service;

import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;

@Service
public interface UserService {

    User registerUser(UserRequestDto userRequestDto);
    
}
