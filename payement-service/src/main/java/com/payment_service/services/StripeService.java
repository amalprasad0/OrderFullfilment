package com.payment_service.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.payment_service.interfaces.IStripePayementService;
import com.payment_service.models.CreatePaymentLink;
import com.payment_service.models.PaymentCheckRequest;
import com.payment_service.models.PaymentLinkResponse;
import com.payment_service.models.Response;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;

@Service
public class StripeService implements IStripePayementService {
    @SuppressWarnings("unused")
    @Override
    public Response<PaymentLinkResponse> generatePaymentLink(CreatePaymentLink createPaymentLink) {
        try {
            Map<String, Object> productData = new HashMap<>();
            productData.put("name", createPaymentLink.getProductName());
            Map<String, Object> priceParams = new HashMap<>();
            priceParams.put("unit_amount", createPaymentLink.unit_amount);
            priceParams.put("currency", createPaymentLink.getCurrency());
            priceParams.put("product_data", productData);
            Price price = Price.create(priceParams);
            Map<String, Object> lineItem = new HashMap<>();
            lineItem.put("price", price.getId());
            lineItem.put("quantity", createPaymentLink.getQuantity());
            Map<String, Object> paymentLinkParams = new HashMap<>();
            paymentLinkParams.put("line_items", List.of(lineItem));
            Map<String, String> metadata = new HashMap<>();
            metadata.put("product_name", createPaymentLink.getProductName());
            metadata.put("price_inr", String.valueOf(createPaymentLink.getUnit_amount()));
            paymentLinkParams.put("metadata", metadata);
            PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
            paymentLinkResponse.setPayementId(paymentLink.getId());
            paymentLinkResponse.setPaymentLink(paymentLink.getUrl());
            if (paymentLink == null) {
                Response.error("Couldn't able to generate Link");
            }
            return Response.success(paymentLinkResponse, "Generated Payment Link");
        } catch (Exception e) {
            return Response.error("Error Generating Payment Link" + e.getMessage());
        }
    }

    public Response<?> checkPaymentStatus(Map<String, String> requestBody) {
        try {
            // Get the payment link ID or session ID from the request
            String sessionId = requestBody.get("sessionId");
            String paymentLinkId = requestBody.get("paymentLinkId");

            if (sessionId == null && paymentLinkId == null) {
                return Response.error("Either sessionId or payementId is null");
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

                return Response.success(response, "Status Retrieved");
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

                return Response.success(response, "Status Retrieved");
            }
        } catch (Exception e) {
            return Response.error("Error on Checking the payment status");
        }

    }
}
