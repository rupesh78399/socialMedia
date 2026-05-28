package com.example.socialmedia;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Testing {

    @GetMapping("/")
    public String home(){
        return "Backend Running";
    }
}

