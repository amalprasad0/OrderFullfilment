package com.order_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order_service.interfaces.IRefundOrderServices;
import com.order_service.models.ApproveRefundOrder;
import com.order_service.models.ApproveRefundResponse;
import com.order_service.models.RequestRefundOrder;
import com.order_service.models.Response;

@RestController
@RequestMapping("/api/v1/refund")
public class RefundController {
    @Autowired IRefundOrderServices refundOrderServices;
    @PostMapping("/request")
    public ResponseEntity<Response<Long>> requestRefundOrder(@RequestBody RequestRefundOrder requestRefundOrder) {
        return ResponseEntity.ok(refundOrderServices.requestRefundOrder(requestRefundOrder));
    }

    @PostMapping("/approve")
    public ResponseEntity<Response<ApproveRefundResponse>> approveRefundOrder(@RequestBody ApproveRefundOrder approveRefundOrder) {
        return ResponseEntity.ok(refundOrderServices.approveRefundOrder(approveRefundOrder));
}
}
