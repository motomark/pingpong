package com.motomark.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    
    @GetMapping("/ping")
    public String sayPing() {
        return "This is Ping!";
    }

    @GetMapping("/pong")
    public String sayPong() {
        return "This is Ping - currently static but will call pong";
    }

}
