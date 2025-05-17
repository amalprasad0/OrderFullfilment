package com.notification_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification_service.interfaces.IEmailService;
import com.notification_service.models.DeliverEmail;
import com.notification_service.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/v1/email")
public class EmailControllers {
    @Autowired
    private IEmailService emailService;
    @PostMapping("deleiverEmail")
    public ResponseEntity<Response<Boolean>> deleiverEmail(@RequestBody DeliverEmail entity , @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        
        return ResponseEntity.ok(emailService.deleiverEmail(entity));
    }
}
