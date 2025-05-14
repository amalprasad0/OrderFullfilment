package com.notification_service.interfaces;

import com.notification_service.models.DeliverEmail;
import com.notification_service.models.Response;

public interface IEmailService {
    public Response<Boolean> deleiverEmail(DeliverEmail entity);
}
