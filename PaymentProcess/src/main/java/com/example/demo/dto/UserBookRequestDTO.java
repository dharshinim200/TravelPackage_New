package com.example.demo.dto;

import com.example.demo.model.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserBookRequestDTO {
    
	private Payment pay;
	private Booking book;
}
