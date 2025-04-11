package com.payment_service.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment_service.interfaces.IStripePayementService;
import com.payment_service.stripeConfig.StripeConfig;

import com.stripe.model.PaymentLink;
import com.stripe.model.Price;


@RestController
@RequestMapping("/api/v1/Payment")
public class StripeController {
    @Autowired
    public IStripePayementService stripeService;
    

    @PostMapping("/create-payment-link")
    public ResponseEntity<?> createPaymentLink() {
        try {
            // Create a Price object first
            Map<String, Object> productData = new HashMap<>();
            productData.put("name", "Mango");
            
            Map<String, Object> priceParams = new HashMap<>();
            priceParams.put("unit_amount", 10000); // 100.00 rupees (in paise)
            priceParams.put("currency", "inr");    // Indian Rupee
            priceParams.put("product_data", productData);
            
            Price price = Price.create(priceParams);
            
            // Create a payment link for this price
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("price", price.getId());
            lineItem.put("quantity", 1);
            
            Map<String, Object> paymentLinkParams = new HashMap<>();
            paymentLinkParams.put("line_items", List.of(lineItem));
            
            // Add custom metadata instead of using after_completion
            Map<String, String> metadata = new HashMap<>();
            metadata.put("product_name", "Mango");
            metadata.put("price_inr", "100");
            paymentLinkParams.put("metadata", metadata);
            
            PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
            
            Map<String, String> response = new HashMap<>();
            response.put("paymentLink", paymentLink.getUrl());
            response.put("paymentLinkId", paymentLink.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check-payment-status")
    public ResponseEntity<?> checkPaymentStatus(@RequestBody Map<String, String> requestBody) {
        try {
            // Get the payment link ID or session ID from the request
            String sessionId = requestBody.get("sessionId");
            String paymentLinkId = requestBody.get("paymentLinkId");

            if (sessionId == null && paymentLinkId == null) {
                return ResponseEntity.badRequest().body("Either sessionId or paymentLinkId is required");
            }

            Map<String, Object> response = new HashMap<>();

            // If a session ID is provided, check payment status directly
            if (sessionId != null) {
                com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.retrieve(sessionId);

                response.put("sessionId", session.getId());
                response.put("paymentStatus", session.getPaymentStatus());
                response.put("status", session.getStatus());
                response.put("isPaymentSuccessful",
                        "paid".equals(session.getPaymentStatus()) && "complete".equals(session.getStatus()));
                response.put("paymentMethod", session.getPaymentMethodCollection());

                // Check if this session was created from a payment link
                if (session.getPaymentLink() != null) {
                    response.put("paymentLinkId", session.getPaymentLink());
                }

                return ResponseEntity.ok(response);
            }

            // If only a payment link ID is provided, list recent sessions for this payment
            // link
            else {
                Map<String, Object> params = new HashMap<>();
                params.put("payment_link", paymentLinkId);
                params.put("limit", 10); // Limit to recent 10 sessions

                com.stripe.model.checkout.SessionCollection sessions = com.stripe.model.checkout.Session.list(params);

                // Process the sessions and return status information
                List<Map<String, Object>> sessionStatusList = new ArrayList<>();

                for (com.stripe.model.checkout.Session session : sessions.getData()) {
                    Map<String, Object> sessionStatus = new HashMap<>();
                    sessionStatus.put("sessionId", session.getId());
                    sessionStatus.put("paymentStatus", session.getPaymentStatus());
                    sessionStatus.put("status", session.getStatus());
                    sessionStatus.put("isPaymentSuccessful",
                            "paid".equals(session.getPaymentStatus()) && "complete".equals(session.getStatus()));
                    sessionStatus.put("created", session.getCreated());
                    sessionStatus.put("customerEmail", session.getCustomerEmail());
                    sessionStatus.put("paymentMethod", session.getPaymentMethodTypes());

                    sessionStatusList.add(sessionStatus);
                }

                response.put("paymentLinkId", paymentLinkId);
                response.put("sessions", sessionStatusList);

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error checking payment status: " + e.getMessage());
        }
    }
}