package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Payment {
	private int paymentId;
	private int userId;
	private int bookingId;
	private int amount;
	private String status;
	private String paymentMethod;
}
