package com.teak.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/6 22:45
 * @Project: teakWeb
 * @File: AESExample.java
 * @Description:
 */
@Component
@Slf4j
public class AESExample {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] originalData = cipher.doFinal(decodedData);
        return new String(originalData);
    }

    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // Java 17支持256位密钥
        SecretKey secretKey = keyGen.generateKey();
        String originalData = "Hello, Java 17!";
        String encryptedData = encrypt(originalData, secretKey);
        String decryptedData = decrypt(encryptedData, secretKey);
        System.out.println("Encrypted: " + encryptedData);
        System.out.println("Decrypted: " + decryptedData);
    }
}
