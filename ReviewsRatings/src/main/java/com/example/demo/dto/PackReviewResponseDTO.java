package com.example.demo.dto;

import com.example.demo.model.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PackReviewResponseDTO {
	 private Pack pack;
	    private Review review;
}
