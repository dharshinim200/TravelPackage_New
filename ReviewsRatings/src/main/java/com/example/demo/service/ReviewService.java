package com.example.demo.service;

import java.util.List;


import com.example.demo.dto.UserReviewResponseDTO;
import com.example.demo.exception.ReviewNotFound;
import com.example.demo.model.Review;


public interface ReviewService {
  
	public abstract String saveReview(Review review);

	public abstract Review updateReview(Review review);

	public abstract String removeReview(int userId);

	public abstract UserReviewResponseDTO getReview(int reviewId) throws ReviewNotFound;

	public abstract List<Review> getAllReview();
	
	public abstract List<Review> getReviewByPackage(int packageId);
	
}
