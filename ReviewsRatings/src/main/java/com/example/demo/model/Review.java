package com.example.demo.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	
	@Id
	private int reviewId;
	private int userId;
	private int packageId;
	@Min(value = 1, message = "Rating must be at least 1")
	@Max(value = 5, message = "Rating must not exceed 5")
	private int rating;
	@NotBlank(message = "Comment cannot be empty")
	private String comment;
	@CreationTimestamp
	private LocalDateTime timestamp;
}
