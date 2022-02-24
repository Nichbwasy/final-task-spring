package com.epam.security.encode;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderGenerator {
    public static BCryptPasswordEncoder generateBCryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
