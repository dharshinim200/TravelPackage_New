package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.dto.Booking;


@FeignClient(name="BOOKINGPROCESS",path="/booking")
public interface BookClient {
  
	@GetMapping("/fetchById/{fid}")
	public Booking checkPayment(@PathVariable("fid") int bookingId);
	
	@PutMapping("/updateStatus/{bookingId}/{status}")
    void updateBookingStatus(@PathVariable("bookingId") int bookingId, @PathVariable("status") String status);
	
}
