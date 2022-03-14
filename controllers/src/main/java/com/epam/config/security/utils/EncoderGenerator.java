package com.epam.config.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderGenerator {
    public static BCryptPasswordEncoder generateBCryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
