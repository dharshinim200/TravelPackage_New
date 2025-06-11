package com.example.demo.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.NotificationRequest;
import com.example.demo.dto.Pack;
import com.example.demo.dto.User;
import com.example.demo.dto.UserBookResponseDTO;
import com.example.demo.exception.BookingNotFound;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.feignclient.NotificationClient;
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
    @Autowired
    private NotificationClient notificationClient;



    /**
     * Saves a new booking to the database.
     * If the package ID is valid, the booking is marked as NOT_PAID and stored.
     * @param booking Booking object to be saved.
     * @return Success message or error message if package validation fails.
     */

    
    @Override
    public Booking saveBooking(Booking booking) {
        log.info("In BookingServiceImpl saveBooking method...");

        try {
            Pack pack = packClient.checkPackage(booking.getPackageId());

            if (pack.getAvailability() > 0) {
                booking.setStatus("NOT_PAID");
                packClient.decreaseAvailability(booking.getPackageId());
                

                Booking savedBooking = repository.save(booking);
                //notify
//                log.info("In BookingServiceImpl saveBooking method...",savedBooking);
//
//                // ✅ Step 1: Fetch user details
//                User user = userClient.getUserId(booking.getUserId());
//
//                // ✅ Step 2: Send confirmation email
//                String subject = "Booking Confirmation - EasyTrips";
//                String message = "Hi " + user.getName() + ",\n\n" +
//                        "Your booking (ID: " + savedBooking.getBookingId() + ") has been successfully created.\n" +
//                        "Package ID: " + booking.getPackageId() + "\n" +
//                        "Start: " + booking.getStartDate() + "\nEnd: " + booking.getEndDate() + "\n" +
//                        "Status: " + booking.getStatus() + "\n\n" +
//                        "Thank you for choosing EasyTrips!";
//                notificationClient.sendEmail(user.getEmail(), subject, message); //notify

                return savedBooking;
            }

        } catch (Exception e) {
            log.error("Error during booking creation", e);
        }

        return null;
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
            String bookingStatus = booking.getStatus();
//            notify
        //    User user = userClient.getUserId(booking.getUserId());

            // ✅ Step 2: Send confirmation email
//            String subject = "Booking Deletion - EasyTrips";
//            String message = "Hi " + user.getName() + ",\n\n" +
//                    "Your booking (ID: " + booking.getBookingId() + ") has been successfully deleted.\n" +
//                    "Package ID: " + booking.getPackageId() + "\n" +
//                    "Start: " + booking.getStartDate() + "\nEnd: " + booking.getEndDate() + "\n" +
//                   "Thank you for choosing EasyTrips!";
//            notificationClient.sendEmail(user.getEmail(), subject, message);
//
//           log.info("In BookingServiceImpl delete booking method ....",subject);
             //Delete booking
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
    
    @Override
    public List<UserBookResponseDTO> getBookingsByUserId(int userId) throws PackageNotFound {
        log.info("In BookingServiceImpl - fetching bookings by userId: {}", userId);

        List<Booking> bookings = repository.findByUserId(userId);
        List<UserBookResponseDTO> result = new ArrayList<>();

        User user = userClient.getUserId(userId); // Fetch once for all
       
        for (Booking booking : bookings) {
            Pack pack = packClient.getPackId(booking.getPackageId());
            result.add(new UserBookResponseDTO(user, booking, pack));
        }

        return result;
    }
    
    public List<UserBookResponseDTO> getBookingsByName(String name) throws PackageNotFound {
        log.info("Fetching bookings by username: {}", name);

        User user = userClient.getBookingsByName(name);
        if (user == null) {
            throw new PackageNotFound("User not found for username: " + name);
        }

        int userId = user.getUserId();
        List<Booking> bookings = repository.findByUserId(userId);

        List<UserBookResponseDTO> result = new ArrayList<>();
        for (Booking booking : bookings) {
            Pack pack = packClient.getPackId(booking.getPackageId());
            result.add(new UserBookResponseDTO(user, booking, pack));
        }

        return result;
    }





}
