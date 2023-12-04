package com.pjay.springbootsecurity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pjay.springbootsecurity.Dtos.UserRequestDto;
import com.pjay.springbootsecurity.Model.User;
import com.pjay.springbootsecurity.Model.VerificationToken;
import com.pjay.springbootsecurity.Service.UserService;
import com.pjay.springbootsecurity.event.RegistrationCompleteEvent;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
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

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + 
                request.getServerName() +
                ":" +
                request.getServerPort()+
                request.getContextPath()
        ;
    }

    private void resendTokenVerificationMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token="+verificationToken.getToken();

        log.info("Click the link to verify your account: {}", url);
    }
}
