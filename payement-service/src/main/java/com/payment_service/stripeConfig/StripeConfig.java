package com.payment_service.stripeConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class StripeConfig {
    private static final Logger logger = LoggerFactory.getLogger(StripeConfig.class);
    
    @Value("${stripe.secret.key}")
    private String secretKey;
    
    @PostConstruct
    public void init() {
        // Log that we've received a key and its first few characters
        if (secretKey != null && !secretKey.isEmpty()) {
            String firstChars = secretKey.substring(0, Math.min(secretKey.length(), 8));
            logger.info("Stripe API key loaded successfully. Key starts with: {}...", firstChars);
        } else {
            logger.error("Stripe API key is empty or null!");
        }
        
        Stripe.apiKey = secretKey;
        logger.info("Stripe API initialization completed");
    }
}