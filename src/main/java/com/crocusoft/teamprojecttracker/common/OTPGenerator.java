package com.crocusoft.teamprojecttracker.common;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
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
    public String generateOTP() {
//        Random random = new Random();
//        return String.format("%06d", random.nextInt(999999));
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
