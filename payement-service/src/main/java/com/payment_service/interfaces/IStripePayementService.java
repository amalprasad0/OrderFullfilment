package com.payment_service.interfaces;

import java.util.Map;

import com.payment_service.models.CreatePaymentLink;
import com.payment_service.models.PaymentCheckRequest;
import com.payment_service.models.PaymentLinkResponse;
import com.payment_service.models.Response;

public interface IStripePayementService {
    public Response<PaymentLinkResponse> generatePaymentLink(CreatePaymentLink  createPaymentLink);
    public Response<?> checkPaymentStatus(Map<String, String> requestBody);
    
}
