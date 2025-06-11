package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.demo.dto.User;

@FeignClient(name="USERROLEMANAGEMENT",path="/user")
public interface UserClient {
    

	
	@GetMapping("/fetchById/{did}")
	public User getUser(@PathVariable("did") int userId);
}
