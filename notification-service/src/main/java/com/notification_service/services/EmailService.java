package com.notification_service.services;

import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notification_service.interfaces.IEmailService;
import com.notification_service.models.DeliverEmail;
import com.notification_service.models.Response;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {
    
    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public Response<Boolean> deleiverEmail(DeliverEmail entity) {
        try {
            
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(entity.getEmail()));
            message.setSubject(entity.getSubject());
            message.setText(entity.getBody(), "UTF-8", "html");  
            message.setSentDate(new Date());
            Transport.send(message);
            return Response.success(true, "Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Failed to send email: " + e.getMessage());
        }
    }
}
