package com.project.notification_service.services;

import com.project.notification_service.events.OrderPlacedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceExample {
    private final JavaMailSender javaMailSender;

    public void sendOrderEmail(OrderPlacedEvent orderPlacedEvent) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("<YOUR '--mail-from' on mailtrap>");
            mimeMessageHelper.setTo("<YOUR '--mail-rcpt' on mailtrap>");
            mimeMessageHelper.setSubject("New Order #" + orderPlacedEvent.getOrderId());

            String body = "<p>Hi bro Le Manh Tuong with UserId: <b>" + orderPlacedEvent.getUserId() + "<b></p>" +
                    "<p>Order #" + orderPlacedEvent.getOrderId() + " has just been successfully created!</p>" +
                    "<p>Total money: <b>$" + orderPlacedEvent.getTotal() + "</b></p>";

            mimeMessageHelper.setText(body, true);
            javaMailSender.send(mimeMessage);

            System.out.println("Email sent successfully");
        } catch (Exception e) {
            System.err.println("Error send email: " + e.getMessage());
        }
    }
}
