package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserPayResponseDTO;
import com.example.demo.exception.PaymentNotFound;
import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService service;

    /**
     * Saves a new payment record in the system.
     * @param payment The payment object to be saved.
     * @return Success message confirming the payment has been saved.
     */
    @PostMapping("/save")
    public String savePayment(@RequestBody @Validated Payment payment) {
        return service.savePayment(payment);
    }

    /**
     * Updates an existing payment record.
     * @param payment The payment object containing updated details.
     * @return The updated payment object.
     */
    @PutMapping("/update")
    public Payment updatePayment(@RequestBody @Validated Payment payment) {
        return service.updatePayment(payment);
    }

    /**
     * Retrieves a payment based on its ID.
     * @param paymentId The ID of the payment to be fetched.
     * @return A DTO containing payment details along with user information.
     * @throws PaymentNotFound If the requested payment does not exist.
     */
    @GetMapping("/fetchById/{fid}")
    public UserPayResponseDTO getPayment(@PathVariable("fid") int paymentId) throws PaymentNotFound {
        return service.getPayment(paymentId);
    }

    /**
     * Retrieves a list of all payments.
     * @return A list containing all payment records.
     */
    @GetMapping("/fetchAll")
    public List<Payment> getAllPayment() {
        return service.getAllPayment();
    }
}
