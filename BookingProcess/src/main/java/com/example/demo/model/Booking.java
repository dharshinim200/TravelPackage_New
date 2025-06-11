package com.example.demo.model;

//Import necessary classes for Spring Boot and Java
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity  
@Table(name = "booking")
@Data  
@NoArgsConstructor
@AllArgsConstructor

public class Booking {

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	private int userId;
	private int packageId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
//	private int paymentId;
}
