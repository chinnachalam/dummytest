package com.learning.dummytest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class GetInfoApplication {


    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        List<String> statusList = new ArrayList<>();
        int langId = 1;
        long timestamp = System.currentTimeMillis();
        for (int userId = 200000; userId < 200999; userId++) {
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
            statusList.add(response.getBody());
        }

        for (String status : statusList) {
            System.out.println(status);
        }
    }
}
