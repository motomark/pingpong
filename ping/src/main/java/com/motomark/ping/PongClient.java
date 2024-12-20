package com.motomark.ping;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

 
// @FeignClient(name = "pong-service", url = "http://pong-service:9700", path = "/")
// We  no longer need to determine the port and the path, Feign will look up from Kubernetes discovery.
@FeignClient(name = "pong-service")
public interface PongClient {
    @GetMapping("/pong")
    public String sayPong();
}