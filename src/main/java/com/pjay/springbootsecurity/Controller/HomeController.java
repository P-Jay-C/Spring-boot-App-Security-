package com.pjay.springbootsecurity.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/hello")
    
    public String hello(){
        return "Hello, Welcome!!!";
    }
}
