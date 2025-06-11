package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.User;

@FeignClient(name="USERROLEMANAGEMENT", path="/user")
public interface UserClient {
    
	@PostMapping("/save")
	public String saveUser(@RequestBody User user);
	
	@GetMapping("/fetchById/{did}")
	public User getUserId(@PathVariable("did") int userId);
	
	@GetMapping("/fetchByName/{name}")
	public User getBookingsByName(@PathVariable("name") String username);
}
