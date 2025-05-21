package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Pack;

@FeignClient(name="travelpackagemanagement",path="/package")
public interface PackClient {
   
	@GetMapping("/fetchById/{fid}")
	public Pack getPackId(@PathVariable("fid") int packageId);
}
