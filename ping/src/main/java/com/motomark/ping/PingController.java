package com.motomark.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @Autowired
    private PongClient pongClient;
    
    @GetMapping("/ping")
    public String sayPing() {
        return "This is Ping!";
    }

    @GetMapping("/pong")
    public String sayPong() {
        return this.pongClient.sayPong();
    }

}
