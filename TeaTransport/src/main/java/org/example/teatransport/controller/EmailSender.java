package org.example.teatransport.controller;

import org.example.teatransport.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class EmailSender {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> payload) {
        emailService.sendEmail(payload.get("email"), payload.get("name"), payload.get("message"));
        return ResponseEntity.ok("Message sent successfully!");
    }



}
