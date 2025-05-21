package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
	private int bookingId;
	private int userId;
	private int packageId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
	private int paymentId;
}
