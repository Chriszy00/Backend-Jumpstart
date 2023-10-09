package com.jumpstart.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.DTO.CheckoutSuccessPayload;
import com.jumpstart.entity.PaymentSummary;
import com.jumpstart.entity.User;
import com.jumpstart.exception.UserNotFoundException;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.service.PaymentSummaryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class PaymentSummaryController {
    @Autowired
    private PaymentSummaryService paymentSummaryService;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/checkout/success")
    public PaymentSummary handleCheckoutSuccess(HttpServletRequest request, @RequestBody CheckoutSuccessPayload payload) {
        // Extract necessary information from the checkout success payload
        String membershipType = payload.getMembershipType();
        double price = payload.getPrice();
        String chosenPaymentMethod = payload.getPaymentMethod(); // Capture the chosen payment method
        String status = "paid"; // Set the status to "paid"

        // Assuming CheckoutSuccessPayload has a getUserId() method
        Long userId = payload.getUserId();

        // Create a PaymentSummary object and save it to the database
        PaymentSummary paymentSummary = new PaymentSummary();
        paymentSummary.setMembershipType(membershipType);
        paymentSummary.setPrice(price);
        paymentSummary.setPaymentMethod(chosenPaymentMethod);
        paymentSummary.setStatus(status);

        // Get the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)); // Throw exception if user is not found

        // Set the user ID in the PaymentSummary
        paymentSummary.setUserId(user.getId());

        // Save the PaymentSummary to the database
        return paymentSummaryService.savePaymentSummary(paymentSummary);
    }


}

