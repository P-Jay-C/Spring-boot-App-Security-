package com.pjay.springbootsecurity.Service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Model.VerificationToken;
import com.pjay.springbootsecurity.Repository.UserRepository;
import com.pjay.springbootsecurity.Repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public User registerUser(UserRequestDto userRequestDto) {
        
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setRole("User");
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userRepository.save(user);
        return user;
    } 

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        
        VerificationToken verificationToken = new VerificationToken(user,token);
        
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateVerificationToken(String token) {
        
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null){
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }


        user.setEnabled(true);;
        userRepository.save(user);

        return "valid";
    }

    @Override
    public com.pjay.springbootsecurity.Model.VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken = verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken) ;

        return verificationToken; 
    }
    
}
