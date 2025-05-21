package com.example.demo.dto;

import com.example.demo.model.Pack;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TravelReviewRequestDTO {
  
	private Review review;
	private Pack pack;
}
