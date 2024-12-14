package com.motomark.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {


     @Value("${ping.message}")
    private String message; 


    @Autowired
    private PongClient pongClient;
    
    @GetMapping("/ping")
    public String sayPing() {
        return "This is Ping!"+ " for " + message;
    }

    @GetMapping("/pong")
    public String sayPong() {
        return this.pongClient.sayPong();
    }

}
