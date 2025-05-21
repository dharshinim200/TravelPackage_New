package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.dto.Payment;

@FeignClient(name="PAYMENTPROCESS",path="/package")
public interface PayClient {
	
	@PostMapping("/payment/save/{bid}/{uid}")
    public Payment processPayment(@PathVariable("bid") int bookingId, @PathVariable("uid") int userId);
	
	 @PutMapping("/updateStatus/{bookingId}/{status}")
	 public String updateBookingStatus(@PathVariable int bookingId, @PathVariable String status);
}
