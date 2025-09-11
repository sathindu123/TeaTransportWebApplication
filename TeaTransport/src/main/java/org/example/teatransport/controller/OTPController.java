package org.example.teatransport.controller;


import lombok.RequiredArgsConstructor;
import org.example.teatransport.service.EmailService;
import org.example.teatransport.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OTPController {
    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-admin-otp")
    public Map<String,Object> sendAdminOtp(@RequestBody Map<String,String> request){
        String email = "sathindusathsarakumara@gmail.com";
        String otp = otpService.generateOTP(email);
        emailService.sendOtpEmail(email, otp);

        Map<String,Object> resp = new HashMap<>();
        resp.put("success", true);
        return resp;
    }

    // Verify OTP
    @PostMapping("/verify-admin-otp")
    public Map<String,Object> verifyAdminOtp(@RequestBody Map<String,String> request){
        String inputOtp = request.get("otp");
        String email = "sathindusathsarakumara@gmail.com";
        boolean verified = otpService.verifyOTP(email, inputOtp);

        Map<String,Object> resp = new HashMap<>();
        resp.put("success", verified);
        return resp;
    }


}
