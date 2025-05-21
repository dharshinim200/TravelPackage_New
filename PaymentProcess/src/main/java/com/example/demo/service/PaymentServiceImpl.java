package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.User;
import com.example.demo.dto.UserPayResponseDTO;
import com.example.demo.exception.PaymentNotFound;
import com.example.demo.feignclient.BookClient;
import com.example.demo.feignclient.UserClient;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;

@Service("bookingService")
public class PaymentServiceImpl implements PaymentService {
	
	Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    PaymentRepository repository;

    @Autowired
    UserClient userClient;
    @Autowired
    BookClient bookClient;

    /**
     * Saves a new payment and updates the booking status to "PAID".
     * @param payment The payment object to be saved.
     * @return Success message confirming payment completion or an error message if service is unavailable.
     */
    @Override
    public String savePayment(Payment payment) {
    	 log.info("In PaymentServiceImpl savePayment method ....");
        try {
            payment.setStatus("PAID");
             repository.save(payment);
            
            bookClient.updateBookingStatus(payment.getBookingId(), "PAID");

            return "Payment completed. Booking status updated to PAID.";
        } catch (Exception e) {
            return "Payment service is unavailable.";
        }
    }

    /**
     * Updates an existing payment record.
     * @param payment The payment object with updated details.
     * @return The updated payment object.
     */
    @Override
    public Payment updatePayment(Payment payment) {
    	 log.info("In PaymentServiceImpl updatePayment method ....");
        return repository.save(payment);
    }

    /**
     * Retrieves payment details along with associated user information.
     * @param paymentId The ID of the payment to fetch.
     * @return A UserPayResponseDTO containing payment and user details.
     * @throws PaymentNotFound If the payment record is not found.
     */
    @Override
    public UserPayResponseDTO getPayment(int paymentId) throws PaymentNotFound {
    	 log.info("In PaymentServiceImpl get Payment method ....");
        Optional<Payment> optionalPayment = repository.findById(paymentId);

        if (optionalPayment.isEmpty()) {
            throw new PaymentNotFound("Payment not found for ID: " + paymentId);
        }

        Payment pay = optionalPayment.get();
        int userNo = pay.getUserId();

        // Fetching user details associated with the payment
        User user = userClient.getUserId(userNo);

        return new UserPayResponseDTO(user, pay);
    }

    /**
     * Retrieves all payment records.
     * @return A list of all payments stored in the database.
     */
    @Override
    public List<Payment> getAllPayment() {
    	 log.info("In PaymentServiceImpl get all Payment method ....");
        return repository.findAll();
    }
}
