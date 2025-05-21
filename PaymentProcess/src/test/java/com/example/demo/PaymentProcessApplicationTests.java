package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.User;
import com.example.demo.dto.UserPayResponseDTO;
import com.example.demo.exception.PaymentNotFound;
import com.example.demo.feignclient.BookClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentServiceImpl;

@SpringBootTest
class PaymentProcessApplicationTests {

	@Mock
	PaymentRepository repository;
	@InjectMocks
	PaymentServiceImpl service;
	@Mock
	BookClient bookClient;
	@Mock
	UserClient userClient;

	@Test
	 void savePaymentSuccessTest() {
	    Payment mockPayment = new Payment();
	    mockPayment.setBookingId(101);
	    mockPayment.setAmount(500);
	    
	    Mockito.when(repository.save(Mockito.any(Payment.class))).thenReturn(mockPayment);
	    Mockito.doNothing().when(bookClient).updateBookingStatus(101, "PAID");

	    String result = service.savePayment(mockPayment);

	    assertEquals("Payment completed. Booking status updated to PAID.", result);
	    assertEquals("PAID", mockPayment.getStatus());
	    Mockito.verify(repository, Mockito.times(1)).save(mockPayment);
	    Mockito.verify(bookClient, Mockito.times(1)).updateBookingStatus(101, "PAID");
	}

	@Test
	 void savePaymentFailureTest() {
	    Payment mockPayment = new Payment();
	    mockPayment.setBookingId(102);
	    
	    Mockito.when(repository.save(Mockito.any(Payment.class))).thenThrow(new RuntimeException("Database error"));

	    String result = service.savePayment(mockPayment);

	    assertEquals("Payment service is unavailable.", result);
	    Mockito.verify(repository, Mockito.times(1)).save(mockPayment);
	    Mockito.verify(bookClient, Mockito.never()).updateBookingStatus(Mockito.anyInt(), Mockito.anyString());
	}

	@Test
	void updatePaymentTest() {
		Payment payment = new Payment(30, 113, 34, 3000, "good", "high");
		Mockito.when(repository.save(payment)).thenReturn(payment);
		Payment updatedPayment = service.updatePayment(payment);
		assertEquals(payment, updatedPayment);
	}

	@Test
	 void getAllPaymentTest() {
		List<Payment> mockPayments = Arrays.asList(new Payment(30, 113, 34, 3000, "PAID", "high"),
				new Payment(2, 113, 34, 3000, "PENDING", "high"));

		Mockito.when(repository.findAll()).thenReturn(mockPayments);

		List<Payment> result = service.getAllPayment();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("PAID", result.get(0).getStatus());
		assertEquals(3000, result.get(0).getAmount());
		assertEquals("PENDING", result.get(1).getStatus());
		assertEquals(3000, result.get(1).getAmount());
	}
	
	@Test
	 void getPaymentSuccessTest() throws PaymentNotFound {
	    int paymentId = 1;
	    Payment mockPayment = new Payment(30, 113, 34, 3000, "PAID", "high");
	    User mockUser = new User(113, "Alice", "Ani@gmail.com", "1234", "customer", "28342947");

	    Mockito.when(repository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
	    Mockito.when(userClient.getUserId(113)).thenReturn(mockUser);

	    UserPayResponseDTO result = service.getPayment(paymentId);

	    assertNotNull(result);
	    assertEquals("Alice", result.getUser().getName());
	    assertEquals("PAID", result.getPay().getStatus());
	    assertEquals(3000, result.getPay().getAmount());
	}

	@Test
	 void getPaymentNotFoundTest() {
	    int paymentId = 2;

	    Mockito.when(repository.findById(paymentId)).thenReturn(Optional.empty());

	    assertThrows(PaymentNotFound.class, () -> service.getPayment(paymentId));
	}


}
