package com.order_service.interfaces;

import com.order_service.models.ApproveRefundOrder;
import com.order_service.models.ApproveRefundResponse;
import com.order_service.models.RequestRefundOrder;
import com.order_service.models.Response;

public interface IRefundOrderServices {
    public Response<Long> requestRefundOrder(RequestRefundOrder requestRefundOrder);
    public Response<ApproveRefundResponse> approveRefundOrder(ApproveRefundOrder approveRefundOrder);
}
