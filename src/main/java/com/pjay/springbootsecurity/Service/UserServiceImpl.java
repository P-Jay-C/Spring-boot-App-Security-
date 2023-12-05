package com.pjay.springbootsecurity.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.PasswordResetToken;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Model.VerificationToken;
import com.pjay.springbootsecurity.Repository.PassswordResetTokenRepository;
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
    @Autowired
    private PassswordResetTokenRepository passswordResetTokenRepository ;

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
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken = verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken) ;

        return verificationToken; 
    }

    @Override
    public User findUserByEmail(String email) {
        
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        
        passswordResetTokenRepository.save(passwordResetToken);

    }

    @Override
    public String validatePasswordResetToken(String token) {
         PasswordResetToken passwordResetToken = passswordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null){
            return "invalid";
        }

        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            passswordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }


        user.setEnabled(true);;
        userRepository.save(user);

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
             
        return Optional.ofNullable(passswordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, oldPassword);
    }
    
}
