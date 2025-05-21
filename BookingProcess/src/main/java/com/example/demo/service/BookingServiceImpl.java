package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Pack;
import com.example.demo.dto.User;
import com.example.demo.dto.UserBookResponseDTO;
import com.example.demo.exception.BookingNotFound;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.feignclient.PackClient;
import com.example.demo.feignclient.PayClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {
    
    Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    
    @Autowired
    BookingRepository repository;

    @Autowired
    UserClient userClient;

    @Autowired
    PackClient packClient;
    @Autowired
    PayClient payClient;

    /**
     * Saves a new booking to the database.
     * If the package ID is valid, the booking is marked as NOT_PAID and stored.
     * @param booking Booking object to be saved.
     * @return Success message or error message if package validation fails.
     */
//    @Override
//    public String saveBooking(Booking booking) {
//        
//        log.info("In BookingServiceImpl saveBooking method ....");
//           try {
//                Pack pack = packClient.checkPackage(booking.getPackageId());
//
//                if (pack == null) {
//                    return "Invalid Package";
//                }
//                booking.setStatus("NOT_PAID");
//                
//                  repository.save(booking);
//               
//                    return "Successfully saved and waiting for payment process";
//                
//
//            } catch (Exception e) {
//                return "Package service not found";
//            }
//    }
    
    @Override
    public String saveBooking(Booking booking) {
       
        log.info("In BookingServiceImpl saveBooking method ....");
        try {
                Pack pack = packClient.checkPackage(booking.getPackageId());

                if (pack == null) {
                    return "Invalid Package";
                }
                  booking.setStatus("NOT_PAID");
                    repository.save(booking);

                    packClient.decreaseAvailability(booking.getPackageId());
               
                    return "Successfully saved and waiting for payment process";
        }
        catch (Exception e) {
          return "Package service not found";
      }
                

    }

    /**
     * Updates the status of a booking.
     * @param bookingId ID of the booking to update.
     * @param status New status to set.
     * @return Success message or error if booking not found.
     */
    public String updateBookingStatus(int bookingId, String status) {
        
        log.info("In BookingServiceImpl update the booking status ....");
        
        Booking booking = repository.findById(bookingId).orElse(null);
        if (booking == null) {
            return "Booking not found";
        }

        booking.setStatus(status);
        repository.save(booking);
        return "Booking status updated successfully to " + status;
    }

    /**
     * Updates an existing booking in the database.
     * @param booking Booking object containing updated details.
     * @return Updated Booking object.
     */
    @Override
    public Booking updateBooking(Booking booking) {
        
        log.info("In BookingServiceImpl updateBooking method ....");
        
        return repository.save(booking);
    }

    /**
     * Removes a booking from the database.
     * @param bookingId ID of the booking to remove.
     * @return Success message confirming deletion.
     */

    public void deleteBooking(int bookingId) throws BookingNotFound {
        Optional<Booking> bookingOpt = repository.findById(bookingId);

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            
            int packageId=bookingOpt.get().getPackageId();
            // Delete booking
            repository.deleteById(bookingId);

            // Increase availability in TravelPackage Service
            packClient.increaseAvailability(packageId);
        } else {
            throw new BookingNotFound("Booking not found with ID: " + bookingId);
        }
    }
    
    
    

    /**
     * Retrieves booking details along with associated user and package details.
     * bookingId ID of the booking to retrieve.
     * @return UserBookResponseDTO containing booking, user, and package details.
     */
    @Override
    public UserBookResponseDTO getBooking(int bookingId) throws PackageNotFound{
        
        log.info("In BookingServiceImpl Listing the Booking details based on the id ....");
        
        Booking book = repository.findById(bookingId).get();
        
        int userno = book.getUserId();
        User user = userClient.getUserId(userno);

        int packno = book.getPackageId();
        Pack pack = packClient.getPackId(packno);
        
        if(userno!=0 && packno!=0) {
        return new UserBookResponseDTO(user, book, pack);
        }
        else {
        	throw new PackageNotFound("Booking not found for ID");
        }
        
    }

    /**
     * Retrieves all bookings from the database.
     * @return List of all Booking objects.
     */
    @Override
    public List<Booking> getAllBooking() {
        
        log.info("In BookingServiceImpl Listing the Booking details ....");

        return repository.findAll();
    }



}
