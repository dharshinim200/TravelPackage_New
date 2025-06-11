package com.example.demo.service;

//Import necessary classes for Spring Boot 
import java.util.List;

import com.example.demo.dto.PackBookResponseDTO;
import com.example.demo.dto.UserBookResponseDTO;
import com.example.demo.exception.BookingNotFound;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.model.Booking;

public interface BookingService {
    
	// Declaration of the methods in booking 
	
	public abstract Booking saveBooking(Booking booking) throws PackageNotFound;

	public abstract Booking updateBooking(Booking booking);

//	public abstract PackBookResponseDTO removeBooking(int bookingId);

	public abstract UserBookResponseDTO getBooking(int bookingId) throws PackageNotFound;

	public abstract List<Booking> getAllBooking();
	
	 public abstract List<UserBookResponseDTO> getBookingsByUserId(int userId) throws PackageNotFound;
   
	 public abstract List<UserBookResponseDTO> getBookingsByName(String name)throws PackageNotFound ;

	public abstract String updateBookingStatus(int bookingId, String status);

	public abstract void deleteBooking(int bookingId) throws BookingNotFound;

	


}
