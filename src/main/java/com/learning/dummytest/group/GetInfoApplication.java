package com.learning.dummytest.group;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetInfoApplication {


    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        List<Integer> userIDs = new ArrayList<>();
        userIDs.add(953257);
        userIDs.add(987204);
        userIDs.add(812029);
        userIDs.add(736588);
        userIDs.add(607072);
        userIDs.add(120711);
        userIDs.add(821637);
        userIDs.add(739689);
        userIDs.add(226416);
        userIDs.add(803461);
        userIDs.add(198534);
        userIDs.add(971337);
        userIDs.add(127395);

        ArrayList<Info> usersInfoResponse = new ArrayList<>();
        Map<Integer, String> usersBalance = new HashMap<>();

        for (Integer userId : userIDs) {
            int langId = 1;
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
            ResponseEntity<Info> response
                = restTemplate.postForEntity(getInfoURL, null, Info.class);
            usersInfoResponse.add(response.getBody());
            usersBalance.put(userId, response.getBody().Balance);

            Thread.sleep(5 * 1000);
        }

        /*for (Info userInfoResponse : usersInfoResponse) {
            if (userInfoResponse != null && userInfoResponse.List != null) {
                String balance = userInfoResponse.Balance;
                if (userInfoResponse != null)
                    for (Transaction transaction : userInfoResponse.List) {
                        if (transaction.actionType == 1) {
                            System.out.println(transaction.userId + "," + balance + "," + transaction.addTime + "," + transaction.Status + "," + transaction.Quantity);
                        }
                    }
            }
        }*/

        for (Map.Entry<Integer, String> userBalance: usersBalance.entrySet()) {
            System.out.println(userBalance.getKey() + ": " + userBalance.getValue());
        }
    }
}
