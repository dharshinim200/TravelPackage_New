package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.dto.Pack;

@FeignClient(name="TRAVELPACKAGEMANAGEMENT",path="/package")
public interface PackClient {
   
	@GetMapping("/fetchById/{fid}")
	public Pack getPackId(@PathVariable("fid") int packageId);
	
	@GetMapping("/fetchById/{fid}")
    public Pack checkPackage(@PathVariable("fid") int packageId);
	
	@PutMapping("/update/{packageId}")
    void increaseAvailability(@PathVariable("packageId") int bookingId);
	
	@PutMapping("/update2/{packageId}")
    void decreaseAvailability(@PathVariable("packageId") int bookingId);
	
}
