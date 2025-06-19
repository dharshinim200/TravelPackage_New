package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.Pack;
import com.example.demo.dto.User;
import com.example.demo.dto.UserReviewResponseDTO;
import com.example.demo.exception.ReviewNotFound;
import com.example.demo.feignclient.PackClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewServiceImpl;

@SpringBootTest
class ReviewsRatingsApplicationTests {

	@Mock
	ReviewRepository repository;
	@InjectMocks
	ReviewServiceImpl service;
	@Mock
	UserClient userClient;
	@Mock
	PackClient packClient;

	@Test
	void saveReviewTest() {
		Review review = new Review(3, 123, 23, 5, "good", LocalDateTime.of(2025, 5, 8, 10, 30));
		Mockito.when(repository.save(review)).thenReturn(review);
		String response = service.saveReview(review);
		assertEquals("Successfully saved", response);
	}

	@Test
	void updateReviewTest() {
		Review review = new Review(110, 123, 23, 5, "good", LocalDateTime.of(2025, 5, 8, 10, 30));
		Mockito.when(repository.save(review)).thenReturn(review);
		Review updatedReview = service.updateReview(review);
		assertEquals(review, updatedReview);
	}

	@Test
	void removeReviewTest() {
		int removeId = 3;
		Mockito.doNothing().when(repository).deleteById(removeId);
		String response = service.removeReview(removeId);
		assertEquals("Deleted successfully", response);
	}

	@Test
	 void getAllReviewTest() {
		List<Review> mockReviews = Arrays.asList(new Review(1, 101, 201, 5, "Great experience!", LocalDateTime.now()),
				new Review(2, 102, 202, 4, "Good service!", LocalDateTime.now()));

		Mockito.when(repository.findAll()).thenReturn(mockReviews);

		List<Review> result = service.getAllReview();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Great experience!", result.get(0).getComment());
		assertEquals(5, result.get(0).getRating());
	}

	@Test
	 void testGetReviewSuccess() throws ReviewNotFound {
		int reviewId = 1;
		Review mockReview = new Review(1, 101, 201, 5, "Great experience!", LocalDateTime.now());
		User mockUser = new User(113, "Ani", "Ani@gmail.com", "1234", "customer", "28342947");
		Pack mockPack = new Pack(201, "Holiday Package", "Luxury", 5, 500, "Includes Meals");

		Mockito.when(repository.findById(reviewId)).thenReturn(Optional.of(mockReview));
		Mockito.when(userClient.getUser(101)).thenReturn(mockUser);
		Mockito.when(packClient.getPackId(201)).thenReturn(mockPack);

		UserReviewResponseDTO result = service.getReview(reviewId);

		assertNotNull(result);
		assertEquals("Ani", result.getUser().getName());
		assertEquals("Great experience!", result.getReview().getComment());
		assertEquals("Holiday Package", result.getPack().getTitle());
	}

	@Test
	 void testGetReviewNotFound() {
		int reviewId = 3;

		Mockito.when(repository.findById(reviewId)).thenReturn(Optional.empty());

		assertThrows(ReviewNotFound.class, () -> service.getReview(reviewId));
	}

}
