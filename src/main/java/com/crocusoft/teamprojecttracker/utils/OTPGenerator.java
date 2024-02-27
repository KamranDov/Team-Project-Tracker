package com.crocusoft.teamprojecttracker.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OTPGenerator {

    //    public String generateOTP()
//
//        Random random = new Random();
//        int otp = random.nextInt(999999);
//        StringBuilder output = new StringBuilder(Integer.toString(otp));
//
//        while (output.length() < 6) {
//            output.insert(0, "0");
//        }
//        return output.toString();
//    }
    public Integer generateOTP() {
        Random random = new Random();
        return (random.nextInt(999999));
    }
}
