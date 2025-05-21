package com.example.demo.dto;

import com.example.demo.model.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserBookRequestDTO {

	private User user;
	private Booking book;
	private Pack pack;
}
