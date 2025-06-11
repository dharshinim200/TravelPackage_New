package com.example.demo.controller;

import java.util.List;

//import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.dto.PackBookResponseDTO;
import com.example.demo.dto.UserBookResponseDTO;
import com.example.demo.exception.BookingNotFound;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    
    @Autowired
    BookingService service;

    /**
     * Saves a new booking.
     * @param booking The booking object to be saved.
     * @return Success message or error message if package validation fails.
     * @throws PackageNotFound If the package associated with the booking is not found.
     */
    @PostMapping("/save")
    public Booking saveBooking(@RequestBody Booking booking) throws PackageNotFound {
        return service.saveBooking(booking);
    }

    /**
     * Updates an existing booking.
     * @param booking The booking object containing updated information.
     * @return The updated booking object.
     */
    @PutMapping("/update")
    public Booking updateBooking(@RequestBody Booking booking) {
        return service.updateBooking(booking);
    }

    /**
     * Deletes a booking by its ID.
     * @param bookingId The ID of the booking to delete.
     * @return Success message confirming deletion.
     * @throws BookingNotFound 
     */

    
    @DeleteMapping("/deleteById/{did}")
    public String deleteBooking(@PathVariable("did") int bookingId) throws BookingNotFound {
        service.deleteBooking(bookingId);
        return "Booking deleted successfully. The payment will be credited within 7 days.";
    }

    /**
     * Retrieves a booking along with associated user and package details.
     * @param bookingId The ID of the booking to fetch.
     * @return A DTO containing user, booking, and package details.
     * @throws PackageNotFound 
     */
    @GetMapping("/fetchById/{fid}")
    public UserBookResponseDTO getBooking(@PathVariable("fid") int bookingId) throws PackageNotFound {
        return service.getBooking(bookingId);
    }

    /**
     * Updates the status of a booking.
     * @param bookingId The ID of the booking whose status is to be updated.
     * @param status The new status to set.
     * @return Success message confirming status update.
     */
    @PutMapping("/updateStatus/{bookingId}/{status}")
    public String updateBookingStatus(@PathVariable("bookingId") int bookingId, @PathVariable("status") String status) {
        return service.updateBookingStatus(bookingId, status);
    }

    /**
     * Retrieves a list of all bookings.
     * @return List containing all booking objects.
     */
    @GetMapping("/fetchAll")
    public List<Booking> getAllBooking() {
        return service.getAllBooking();
    }
    
    @GetMapping("/fetchByUserId/{userId}")
    public List<UserBookResponseDTO> getBookingsByUser(@PathVariable("userId") int userId) throws PackageNotFound {
        return service.getBookingsByUserId(userId);
    }
    
    @GetMapping("/fetchByName/{name}")
    public ResponseEntity<List<UserBookResponseDTO>> getBookingsByName(@PathVariable String name) {
        try {
            List<UserBookResponseDTO> bookings = service.getBookingsByName(name);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (PackageNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
