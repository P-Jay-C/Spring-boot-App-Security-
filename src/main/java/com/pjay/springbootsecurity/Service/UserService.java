package com.pjay.springbootsecurity.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Model.VerificationToken;

@Service
public interface UserService {

    User registerUser(UserRequestDto userRequestDto);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
    
}
