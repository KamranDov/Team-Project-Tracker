package com.crocusoft.teamprojecttracker.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OTP {

    public String generateOTP() {
//        Random random = new Random();
//        int otp = random.nextInt(999999);
//        StringBuilder output = new StringBuilder(Integer.toString(otp));
//
//        while (output.length() < 6) {
//            output.insert(0, "0");
//        }
//        return output.toString();
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
}
