package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.Pack;
import com.example.demo.dto.User;
import com.example.demo.dto.UserBookResponseDTO;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.feignclient.PackClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.BookingServiceImpl;

@SpringBootTest
class BookingPaymentApplicationTests {
    
	@Mock
	BookingRepository repository;
	
	@InjectMocks
	BookingServiceImpl service;
	@Mock
	PackClient packClient;
	@Mock
	UserClient userClient;
	
	@Test
	void saveBookingSuccessTest() {
	    Booking mockBooking = new Booking();
	    mockBooking.setPackageId(101);
	    mockBooking.setUserId(1);

	    Pack mockPack = new Pack(101, "Deluxe Package", "Luxury", 5, 5000,5, "Includes Meals");

	    Mockito.when(packClient.checkPackage(mockBooking.getPackageId())).thenReturn(mockPack);
	    Mockito.when(repository.save(Mockito.any(Booking.class))).thenReturn(mockBooking);

	    String result = service.saveBooking(mockBooking);

	    assertEquals("Successfully saved and waiting for payment process", result);
	    assertEquals("NOT_PAID", mockBooking.getStatus());
	    Mockito.verify(repository, Mockito.times(1)).save(mockBooking);
	    Mockito.verify(packClient, Mockito.times(1)).checkPackage(101);
	}

	@Test
	 void saveBookingInvalidPackageTest() {
	    Booking mockBooking = new Booking();
	    mockBooking.setPackageId(202);

	    Mockito.when(packClient.checkPackage(mockBooking.getPackageId())).thenReturn(null);

	    String result = service.saveBooking(mockBooking);

	    assertEquals("Invalid Package", result);
	    Mockito.verify(repository, Mockito.never()).save(Mockito.any(Booking.class));
	}

	@Test
	 void saveBookingFailureTest() {
	    Booking mockBooking = new Booking();
	    mockBooking.setPackageId(101);

	    Mockito.when(packClient.checkPackage(mockBooking.getPackageId())).thenThrow(new RuntimeException("Service error"));

	    String result = service.saveBooking(mockBooking);

	    assertEquals("Package service not found", result);
	    Mockito.verify(repository, Mockito.never()).save(Mockito.any(Booking.class));
	}

	@Test
	void updateBookTest() {
		Booking book = new Booking(30,113,30,LocalDate.of(2025, 07, 12),LocalDate.of(2025, 07, 12),"PAID", 2);
		Mockito.when(repository.save(book)).thenReturn(book);
		Booking updatedPayment = service.updateBooking(book);
		assertEquals(book, updatedPayment);
	}
	
	@Test
    void testUpdateBookingStatusSuccess() {
        int bookingId = 1;
        String newStatus = "Confirmed";

        Booking booking = new Booking();
        booking.setUserId(bookingId);
        booking.setStatus("Pending");

        when(repository.findById(bookingId)).thenReturn(java.util.Optional.of(booking));

        String result = service.updateBookingStatus(bookingId, newStatus);

        assertEquals("Booking status updated successfully to " + newStatus, result);
        assertEquals(newStatus, booking.getStatus());
        verify(repository).save(booking);
    }

    @Test
    void testUpdateBookingStatusBookingNotFound() {
        int bookingId = 2;
        String newStatus = "Cancelled";

        when(repository.findById(bookingId)).thenReturn(java.util.Optional.empty());

        String result = service.updateBookingStatus(bookingId, newStatus);

        assertEquals("Booking not found", result);
        verify(repository, never()).save(any());
    }


//	@Test
//	void removeBookTest() {
//		int bookingId=30;
//		
//		Mockito.doNothing().when(repository).deleteById(bookingId);
//		String response = service.removeBooking(bookingId);
//		assertEquals("deleted successfully", response);
//	}
	
	@Test
	 void getAllBookingTest() {
	    List<Booking> mockBookings = Arrays.asList(
	        new Booking(1, 101, 202, LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 15), "NOT_PAID", 555),
	        new Booking(2, 102, 203, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 7), "PAID", 556)
	    );

	    Mockito.when(repository.findAll()).thenReturn(mockBookings);

	    List<Booking> result = service.getAllBooking();

	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals(101, result.get(0).getUserId());
	    assertEquals("NOT_PAID", result.get(0).getStatus());
	    assertEquals(102, result.get(1).getUserId());
	    assertEquals("PAID", result.get(1).getStatus());
	}
   
	@Test
    void testGetBookingSuccess() throws PackageNotFound {
        int bookingId = 1;

        Booking booking = new Booking();
        booking.setUserId(bookingId);
        booking.setUserId(101);
        booking.setPackageId(202);

        User user = new User();
        user.setUserId(101);
        user.setName("John Doe");

        Pack pack = new Pack();
        pack.setPackageId(202);
        pack.setTitle("Holiday Package");

        when(repository.findById(bookingId)).thenReturn(java.util.Optional.of(booking));
        when(userClient.getUserId(101)).thenReturn(user);
        when(packClient.getPackId(202)).thenReturn(pack);

        UserBookResponseDTO responseDTO = service.getBooking(bookingId);

        assertNotNull(responseDTO);
        assertEquals(user, responseDTO.getUser());
        assertEquals(booking, responseDTO.getBook());
        assertEquals(pack, responseDTO.getPack());
    }

    @Test
    void testGetBookingNotFound() {
        int bookingId = 2;

        when(repository.findById(bookingId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getBooking(bookingId));
    }
}
