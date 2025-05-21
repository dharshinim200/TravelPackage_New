package com.example.demo.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Review;

@FeignClient(name = "REVIEWSRATINGS",path="/review")
public interface ReviewClient {
	
	@DeleteMapping("/deleteById/{did}")
    void removeReview(@PathVariable("did") int reviewId);
	
	@GetMapping("/fetchReviewByPackage/{pid}")
	public List<Review> getReviewByPackage(@PathVariable("pid") int packageId);
}
