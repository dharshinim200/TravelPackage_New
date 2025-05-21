package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserReviewResponseDTO;
import com.example.demo.exception.ReviewNotFound;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService service;

	@PostMapping("/save")
	public String saveReview(@RequestBody @Validated Review review) {
		return service.saveReview(review);
	}

	@PutMapping("/update")
	public Review updateReview(@RequestBody @Validated Review review) {
		return service.updateReview(review);
	}

	@DeleteMapping("/deleteById/{did}")
	public String removeReview(@PathVariable("did") int reviewId) {
		return service.removeReview(reviewId);
	}

	@GetMapping("/fetchById/{fid}")
	public UserReviewResponseDTO getReview(@PathVariable("fid") int reviewId) throws ReviewNotFound {
		return service.getReview(reviewId);
	}
	
	@GetMapping("/fetchReviewByPackage/{pid}")
	public List<Review> getReviewByPackage(@PathVariable("pid") int packageId) {
		return service.getReviewByPackage(packageId);
	}

	@GetMapping("/fetchAll")
	public List<Review> getAllReview() {
		return service.getAllReview();
	}
}
