package com.learning.dummytest.login;

import com.learning.dummytest.PMDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginApplication {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        List<String> statusList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("8631888510", "8631888510");
        userDetails.put("8631888511", "8631888511");
        userDetails.put("8631888509", "8631888509");
        userDetails.put("8631688509", "8631688509");
        userDetails.put("9848022338", "9848022338");
        userDetails.put("7785617458", "7785617458");
        userDetails.put("9568745623", "9568745623");
        userDetails.put("9491356241", "9491356241");
        userDetails.put("7785296321", "7785296321");
        userDetails.put("8569235471", "8569235471");
        userDetails.put("6235845628", "6235845628");
        userDetails.put("9818586312", "9818586312");
        userDetails.put("9465897451", "9465897451");
        userDetails.put("9701589647", "9701589647");
        userDetails.put("6010256341", "6010256341");
        userDetails.put("9848635289", "9848635289");
        userDetails.put("8795632589", "8795632589");
        userDetails.put("8869574136", "8869574136");
        userDetails.put("6312478956", "6312478956");
        userDetails.put("7854612387", "7854612387");
        userDetails.put("9491625040", "test@12345");
        userDetails.put("9381833176", "test@12345");
        userDetails.put("9553184313", "test@12345");
        userDetails.put("9553184313", "test@12345");
        userDetails.put("7702094679", "test@12345");

        for (Map.Entry<String, String> userDetail : userDetails.entrySet()) {
            String signedPassword = getMD5(userDetail.getValue());
            long timestamp = System.currentTimeMillis();
            String plaintext = String.format(
                "Phone=%s&langId=1&timestamp=%d&userPwd=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
                userDetail.getKey(), timestamp, signedPassword);
            String sign = getMD5(plaintext);

            String loginUrl = String.format(
                "https://api.amlvip-in.com/api/Login/doLogin" +
                    "?langId=1&Phone=%s&timestamp=%d&userPwd=%s&sign=%s",
                userDetail.getKey(), timestamp, signedPassword, sign
            );
            ResponseEntity<String> loginResponse
                = restTemplate.getForEntity(loginUrl, String.class);
            statusList.add(userDetail.getKey() + ":" + loginResponse.getBody());
        }


        for (String status : statusList) {
            System.out.println(status);
        }
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
