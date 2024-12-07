package com.motomark.ping;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "pong-service")
public interface PongClient {
    @GetMapping("/pong")
    public String sayPong();
}