package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Pack;
import com.example.demo.dto.User;
import com.example.demo.dto.UserReviewResponseDTO;
import com.example.demo.exception.ReviewNotFound;
import com.example.demo.feignclient.PackClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

@Service("userService")
public class ReviewServiceImpl implements ReviewService {
   
	Logger log=LoggerFactory.getLogger(ReviewServiceImpl.class);
    @Autowired
    ReviewRepository repository;
    @Autowired
    UserClient userClient;
    @Autowired
    PackClient packClient;

    /**
     * Saves a new review to the database.
     * @param review Review object to be saved.
     * @return Success message if saved, otherwise an error message.
     */
    @Override
    public String saveReview(Review review) {
    	 log.info("In ReviewServiceImpl saveReview method ....");
           repository.save(review);
       
            return "Successfully saved";
    }

    /**
     * Updates an existing review in the database.
     * @param review Review object containing updated details.
     * @return Updated Review object.
     */
    @Override
    public Review updateReview(Review review) {
    	 log.info("In ReviewServiceImpl update Review method ....");
        return repository.save(review);
    }

    /**
     * Removes a review from the database based on its ID.
     * @param reviewId ID of the review to be removed.
     * @return Success message confirming deletion.
     */
    @Override
    public String removeReview(int reviewId) {
    	 log.info("In RemoveServiceImpl remove review method ....");
        repository.deleteById(reviewId);
        return "Deleted successfully";
    }

    /**
     * Retrieves review details along with associated user and package details.
     * @param reviewId ID of the review to retrieve.
     * @return UserReviewResponseDTO containing user, review, and package details.
     * @throws ReviewNotFound If the review is not found.
     */
    @Override
    public UserReviewResponseDTO getReview(int reviewId) throws ReviewNotFound {
    	 log.info("In ReviewServiceImpl get Review method ....");
        Optional<Review> optionalReview = repository.findById(reviewId);

        if (optionalReview.isEmpty()) {
            throw new ReviewNotFound("Review not found for ID: " + reviewId);
        }

        Review review = optionalReview.get();
        int packageId = review.getPackageId();
        int userNo = review.getUserId();
        User user = userClient.getUserId(userNo);
        Pack pack = packClient.getPackId(packageId);

        return new UserReviewResponseDTO(user, review, pack);
    }

    /**
     * Retrieves all reviews from the database.
     * @return List of all Review objects.
     */
    @Override
    public List<Review> getAllReview() {
    	 log.info("In ReviewServiceImpl get all Review method ....");
        return repository.findAll();
    }

    /**
     * Retrieves all reviews associated with a specific package ID.
     * @param packageId ID of the package for which reviews are retrieved.
     * @return List of Review objects related to the specified package.
     */
    @Override
    public List<Review> getReviewByPackage(int packageId) {
    	 log.info("In ReviewServiceImpl get Review by package method ....");
        return repository.findByPackageId(packageId);
    }

}
