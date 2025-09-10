package org.example.teatransport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String toEmail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Admin OTP Code");
        message.setText("Your OTP code is: " + otp);
        javaMailSender.send(message);
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage emailmessage = new SimpleMailMessage();

        emailmessage.setTo(to);
        emailmessage.setTo("sathindusathsarakumara@gmail.com");
        emailmessage.setSubject("New Contact Form Message from"+subject);
        emailmessage.setText(message);
        javaMailSender.send(emailmessage);
    }



}
