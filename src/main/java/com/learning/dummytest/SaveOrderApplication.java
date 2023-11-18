package com.learning.dummytest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SaveOrderApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        int userId = 801560;
        String userPwd = "31632dd470561e3c9af75914ad868ed0";
        String orderSecPassword = "902fbdd2b1df0c4f70b4a5d23525e932";
        long timestamp = System.currentTimeMillis();
        String coin = "20231110420"; //TODO
        int resultChannel = 1; //TODO
        int orderQuantity = 1000;
        //int orderQuantity = 199;

        String plaintext = String.format("coin=%s&langId=1&order_SecrityPwd=%s&order_expiryTime=1&order_index=%d&order_quantity=%d&order_updown=1&timestamp=%d&userId=%d&userPwd=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
            coin, orderSecPassword, resultChannel, orderQuantity, timestamp, userId, userPwd);
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String sign = bigInt.toString(16);
        System.out.println(plaintext);

        String orderSaveUrl = String.format(
            "https://api.amlvip-in.com/api/Quotes/orderSave?coin=%s&userId=%d&userPwd=%s&order_index=%d&order_quantity=%d&order_SecrityPwd=%s&order_updown=1&order_expiryTime=1&langId=1&timestamp=%d&sign=%s",
            coin, userId, userPwd, resultChannel, orderQuantity, orderSecPassword, timestamp, sign
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
            = restTemplate.postForEntity(orderSaveUrl, null, String.class);
    }
}
