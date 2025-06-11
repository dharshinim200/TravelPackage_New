package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="NOTIFICATION",path="/notify")
public interface  NotificationClient {

	@PostMapping("/send")
    String sendEmail(@RequestParam("recipient") String recipient,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message);
}
