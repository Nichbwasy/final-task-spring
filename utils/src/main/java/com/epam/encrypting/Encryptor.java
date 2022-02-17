package com.epam.encrypting;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Encryptor {
    public static String encryptSHA256(String str) {
        return Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString();
    }
}
