package com.motomark.ping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    Logger log = LoggerFactory.getLogger(PingController.class);


     @Value("${ping.message}")
    private String message; 


    @Autowired
    private PongClient pongClient;
    
    @GetMapping("/ping")
    public String sayPing() {
        String out = "This is Ping!"+ " for " + message;
        log.info(out);
        return out;
    }

    @GetMapping("/pong")
    public String sayPong() {
        return this.pongClient.sayPong();
    }

}
