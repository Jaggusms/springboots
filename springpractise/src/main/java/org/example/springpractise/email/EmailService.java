package org.example.springpractise.email;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    public void sendEmail() {

        try {
            // 1. Configure the SMTP server
            String host = "your-smtp-server";
            String from = "sender@example.com";
            String to = "recipient@example.com";

            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host", host);

            // 2. Create a mail session
            Session session = Session.getInstance(properties);

            // 3. Create the email
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );

            message.setSubject("Sample Email");
            message.setText("Hello! This is a sample email from Spring Boot.");

            // 4. Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}