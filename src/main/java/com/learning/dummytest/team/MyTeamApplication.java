package com.learning.dummytest.team;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyTeamApplication {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //userId=935921&langId=1&timestamp=1700156286229&sign=897ddf6a6e5e83614ed22b9f8c6305d5

        //https://api.amlvip-in.com/api/Mine/myTeam
        RestTemplate restTemplate = new RestTemplate();

        String userId = "449865";

        long timestamp = System.currentTimeMillis();
        String plaintext = String.format(
            "langId=1&timestamp=%d&userId=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
            timestamp, userId);
        String sign = getMD5(plaintext);

        String myTeamUrl = String.format(
            "https://api.amlvip-in.com/api/Mine/myTeam" +
                "?langId=1&timestamp=%d&userId=%s&sign=%s",
            timestamp, userId, sign
        );
        ResponseEntity<String> myTeamResponse
            = restTemplate.getForEntity(myTeamUrl, String.class);
        System.out.println(myTeamResponse.getBody());
    }

    public static String getMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
