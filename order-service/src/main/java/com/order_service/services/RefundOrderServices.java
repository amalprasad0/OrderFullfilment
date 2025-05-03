package com.order_service.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.order_service.components.PaymentComponents;
import com.order_service.entity.Orders;
import com.order_service.entity.RefundedOrders;
import com.order_service.entity.RequestRefundOrders;
import com.order_service.interfaces.IRefundOrderServices;
import com.order_service.models.ApproveRefundOrder;
import com.order_service.models.ApproveRefundResponse;
import com.order_service.models.RequestRefundOrder;
import com.order_service.models.Response;
import com.order_service.repository.OrdersRepository;
import com.order_service.repository.RefundedOrdersRepo;
import com.order_service.repository.RequestRefundOrdersRepo;

import feign.Request;
@Service
public class RefundOrderServices implements IRefundOrderServices {
    @Autowired
    private RefundedOrdersRepo refundOrderRepo;
    @Autowired
    private RequestRefundOrdersRepo requestRefundOrderRepo;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired 
    private PaymentComponents paymentComponents;

    @Override
    public Response<Long> requestRefundOrder(RequestRefundOrder requestRefundOrder) {
        try {
            Orders order = ordersRepository.findById(requestRefundOrder.getOrderId()).orElse(null);
            if (order == null) {
                return Response.error("Order not found with ID: " + requestRefundOrder.getOrderId());
            }
            if (order.getOrderStatus().equals("pending")) {
                return Response.error("Order is still pending, cannot process refund.");
            }

            RequestRefundOrders RequestForRefundOrder = new RequestRefundOrders();
            RequestForRefundOrder.setOrderId(order);
            RequestForRefundOrder.setRefundReason(requestRefundOrder.getRefundReason());
            RequestForRefundOrder.setRefundStatus("pending");
            var savedRequestRefundOrder = requestRefundOrderRepo.save(RequestForRefundOrder);
            if (savedRequestRefundOrder == null) {
                return Response.error("Couldn't able to request refund order");
            }
            if (savedRequestRefundOrder.getId() == null) {
                return Response.error("Couldn't able to request refund order");
            }

            return Response.success(savedRequestRefundOrder.getId(), "Refund order requested successfully with ID: ");
        } catch (Exception e) {
            return Response.error("Error while requesting refund order: " + e.getMessage());
        }
    }

    @Override
    public Response<ApproveRefundResponse> approveRefundOrder(ApproveRefundOrder approveRefundOrder) {
        try {
            if (approveRefundOrder.getRefundStatus().equals("approved")) {
                RequestRefundOrders requestRefundOrder = requestRefundOrderRepo
                        .findAll().stream().filter(x-> x.getId().equals(approveRefundOrder.getRequestRefundId())).findFirst().orElse(null);
                if (requestRefundOrder == null) {
                    return Response.error(
                            "Request refund order not found with ID: " + approveRefundOrder.getRequestRefundId());
                }
                Map<String, Object> refundResponse = paymentComponents.refundPayment(requestRefundOrder.getOrderId().getId());

                if (refundResponse == null || !Boolean.TRUE.equals(refundResponse.get("success"))) {
                    return Response.error("Couldn't refund the order. [Error from Payment Service]");
                }
                String refundId = (String) refundResponse.get("refundId");
                if (refundId == null) {
                    return Response.error("Couldn't refund the order. [Error from Payment Service]");
                }

                requestRefundOrder.setRefundStatus(approveRefundOrder.getRefundStatus());
                var savedRequestRefundOrder = requestRefundOrderRepo.save(requestRefundOrder);
                if (savedRequestRefundOrder == null) {
                    return Response.error("Couldn't able to approve refund order");
                }
                if (savedRequestRefundOrder.getId() == null) {
                    return Response.error("Couldn't able to approve refund order");
                }
                RefundedOrders refundOrder = new RefundedOrders();
                refundOrder.setRequestedRefundId(requestRefundOrder);
                refundOrder.setRefundTransactionId(refundId);
                var refundedOrder = refundOrderRepo.save(refundOrder);
                if (refundedOrder == null) {
                    return Response.error("Couldn't able to approve refund order");
                }
                if (refundedOrder.getId() == null) {
                    return Response.error("Couldn't able to approve refund order");
                }
                ApproveRefundResponse approveRefundResponse = new ApproveRefundResponse();
                approveRefundResponse.setRequestRefundId(refundOrder.getId());
                approveRefundResponse.setRefundStatus(refundedOrder.getRequestedRefundId().getRefundStatus());
                approveRefundResponse.setRefundReason(refundedOrder.getRequestedRefundId().getRefundReason());

                return Response.success(approveRefundResponse, "Refund order approved successfully ");
            }else{
                return Response.error("Refund order is not approved yet.");
            }
        } catch (Exception e) {
            return Response.error("Error while approving refund order: " + e.getMessage());
        }
    }
}