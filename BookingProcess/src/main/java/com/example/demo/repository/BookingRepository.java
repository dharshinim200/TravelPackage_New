package com.example.demo.repository;

import java.util.List;

//Import necessary classes for Spring Boot
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Booking;

//Marks this interface as a Spring repository.
@Repository

//Provides built-in CRUD operations without needing explicit queries.

public interface BookingRepository extends JpaRepository<Booking , Integer>{
 
	List<Booking> findByUserId(int userId);
}
