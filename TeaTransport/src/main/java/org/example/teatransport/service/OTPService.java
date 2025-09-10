package org.example.teatransport.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    private ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOTP(String email){
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOTP(String email, String inputOtp){
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(inputOtp);
    }

}
