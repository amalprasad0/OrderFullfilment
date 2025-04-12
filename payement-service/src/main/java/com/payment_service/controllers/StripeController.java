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
import com.payment_service.models.CreatePaymentLink;
import com.payment_service.models.PaymentLinkResponse;
import com.payment_service.models.Response;
import com.payment_service.stripeConfig.StripeConfig;

import com.stripe.model.PaymentLink;
import com.stripe.model.Price;


@RestController
@RequestMapping("/api/v1/Payment")
public class StripeController {
    @Autowired
    public IStripePayementService stripeService;
    

    @PostMapping("/create-payment-link")
    public ResponseEntity<Response<PaymentLinkResponse>> generatePaymentLink(@RequestBody CreatePaymentLink entity){
        return ResponseEntity.ok(stripeService.generatePaymentLink(entity));
    }
    @PostMapping("/check-payment-status")
    public ResponseEntity<Response<?>> getPaymentStatus(@RequestBody Map<String, String> requestBody){
        return ResponseEntity.ok(stripeService.checkPaymentStatus(requestBody));
    }
}