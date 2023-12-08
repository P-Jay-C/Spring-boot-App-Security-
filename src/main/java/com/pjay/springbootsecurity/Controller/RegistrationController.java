package com.pjay.springbootsecurity.Controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pjay.springbootsecurity.Dtos.PasswordModel;
import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Model.VerificationToken;
import com.pjay.springbootsecurity.Service.UserService;
import com.pjay.springbootsecurity.event.RegistrationCompleteEvent;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    
    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequestDto userRequestDto, final HttpServletRequest request){
        User user = userService.registerUser(userRequestDto);
        publisher.publishEvent(
            new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/resendToken")
    public String resendVerification(@RequestParam("token") String oldToken, HttpServletRequest request){
        
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        User user = verificationToken.getUser();
        
        resendTokenVerificationMail(user, applicationUrl(request), verificationToken);

        return "Verification link sent";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){

        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "User verification sucessfull";
        }

        return "Not verified";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        User user = userService.findUserByEmail(passwordModel.getEmail());

        String url = "";  

        if(user != null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);

            
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){

        String result = userService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid ")){
            return "Invalid Token";
        }
        
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password reset successful";
        }else{
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        User user = userService.findUserByEmail(passwordModel.getEmail());

        if(!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())){
            return "Invalid old Password";
        }

        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password Change Sucessful";
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/api/v1/auth/savePassword?token="+token;

        log.info("Click the link to reset your password: {}", url);
        
        return url;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + 
                request.getServerName() +
                ":" +
                request.getServerPort()+
                request.getContextPath()
        ;
    }

    private void resendTokenVerificationMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/api/v1/auth/verifyRegistration?token="+verificationToken.getToken();

        log.info("Click the link to verify your account: {}", url);
    }
}
