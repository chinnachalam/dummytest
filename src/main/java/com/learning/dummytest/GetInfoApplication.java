package com.learning.dummytest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class GetInfoApplication {


    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        int langId = 1;
        int userId = 261491;
        long timestamp = System.currentTimeMillis();

        String plaintext = String.format("langId=1&timestamp=%d&userId=%d&key=19076bcd2fba4a1e2c5aba0b7497e06e",
            timestamp, userId);
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String sign = bigInt.toString(16);
        System.out.println(plaintext);

        RestTemplate restTemplate = new RestTemplate();
        String getInfoURL = String.format(
            "https://api.amlvip-in.com/api/Assets/getInfo?userId=%d&langId=%d&timestamp=%d&sign=%s",
            userId, langId, timestamp, sign
        );
        ResponseEntity<String> response
            = restTemplate.postForEntity(getInfoURL, null, String.class);
        System.out.println(response.getBody());

    }
}
