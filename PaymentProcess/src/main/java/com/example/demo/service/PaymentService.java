package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserPayResponseDTO;
import com.example.demo.exception.PaymentNotFound;
import com.example.demo.model.Payment;



public interface PaymentService {
  
	public abstract String savePayment(Payment payment);

	public abstract Payment updatePayment(Payment payment);

	public abstract UserPayResponseDTO getPayment(int paymentId) throws PaymentNotFound;

	public abstract List<Payment> getAllPayment();
	
}
