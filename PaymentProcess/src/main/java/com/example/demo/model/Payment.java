package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	@Id
	private int paymentId;
	private int userId;
	private int bookingId;
	@Min(value = 1, message = "Amount must be at least 1")
	private int amount;
	private String status;
	@NotBlank(message = "Payment method cannot be empty")
	private String paymentMethod;
}
