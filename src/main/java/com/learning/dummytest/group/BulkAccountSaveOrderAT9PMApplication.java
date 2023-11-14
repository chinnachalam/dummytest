package com.learning.dummytest.group;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.RunData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BulkAccountSaveOrderAT9PMApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {

        Map<Integer, String> userDetails = new HashMap<>();

        userDetails.put(226416, "test@12345");
        userDetails.put(803461, "test@12345");
        userDetails.put(198534, "test@12345");
        userDetails.put(127395, "test@12345");

        userDetails.put(971337, "sangeetha@13");

        userDetails.put(953257, "8631888510");
        userDetails.put(987204, "8631888511");
        userDetails.put(812029, "8631888509");
        userDetails.put(736588, "8631688509");


        List<Integer> successIds = new ArrayList<>();

        String orderSecPassword = "902fbdd2b1df0c4f70b4a5d23525e932";
        int resultChannel = 2;//TODO
        String coin = "20231114420"; // TODO
        long timestamp = System.currentTimeMillis();

        BulkAccountSaveOrderAT9PMApplication bulkAccountSaveOrderApplication = new BulkAccountSaveOrderAT9PMApplication();
        RestTemplate restTemplate = new RestTemplate();


        ArrayList<String> responseData = new ArrayList<>();

        for (Map.Entry<Integer, String> userDetail : userDetails.entrySet()) {
            int userId = userDetail.getKey();
            String userPwd = bulkAccountSaveOrderApplication.getMD5(userDetail.getValue());


            String getInfoPlainText = String.format("langId=1&timestamp=%d&userId=%d&key=19076bcd2fba4a1e2c5aba0b7497e06e",
                timestamp, userId);
            String getInfoSign = bulkAccountSaveOrderApplication.getMD5(getInfoPlainText);

            String getInfoURL = String.format(
                "https://api.amlvip-in.com/api/Assets/getInfo?userId=%d&langId=%d&timestamp=%d&sign=%s",
                userId, 1, timestamp, getInfoSign
            );

            ResponseEntity<Info> getInfoResponse
                = restTemplate.postForEntity(getInfoURL, null, Info.class);

            if (getInfoResponse == null || getInfoResponse.getBody() == null || getInfoResponse.getBody().Balance == null) {
                continue;
            }

            long orderQuantity = Math.round((Double.valueOf(getInfoResponse.getBody().Balance) * 15) / 100);

            System.out.println(getInfoResponse.getBody().Balance + ":" + orderQuantity);

            String plaintext = String.format("coin=%s&langId=1&order_SecrityPwd=%s&order_expiryTime=1&order_index=%d&order_quantity=%d&order_updown=1&timestamp=%d&userId=%d&userPwd=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
                coin, orderSecPassword, resultChannel, orderQuantity, timestamp, userId, userPwd);
            String sign = bulkAccountSaveOrderApplication.getMD5(plaintext);

            String orderSaveUrl = String.format(
                "https://api.amlvip-in.com/api/Quotes/orderSave?coin=%s&userId=%d&userPwd=%s&order_index=%d&order_quantity=%d&order_SecrityPwd=%s&order_updown=1&order_expiryTime=1&langId=1&timestamp=%d&sign=%s",
                coin, userId, userPwd, resultChannel, orderQuantity, orderSecPassword, timestamp, sign
            );

            ResponseEntity<String> response
                = restTemplate.postForEntity(orderSaveUrl, null, String.class);
            responseData.add(userId + ":" + response.getBody());
            Thread.sleep(2 * 1000);
        }

        for (String response : responseData) {
            System.out.println(response);
        }

    }


    public String getMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }
}
