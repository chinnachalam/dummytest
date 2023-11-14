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
import java.util.Map;


public class BulkAccountSaveOrderApplication01 {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {

        Map<Integer, String> userDetails = new HashMap<>();
        userDetails.put(607072, "7785617458");
        // userDetails.put(120711, "9568745623");
        userDetails.put(821637, "9491356241");
        userDetails.put(739689, "7785296321");

        int orderQuantity = 4000;
        String orderSecPassword = "902fbdd2b1df0c4f70b4a5d23525e932";

        BulkAccountSaveOrderApplication01 bulkAccountSaveOrderApplication = new BulkAccountSaveOrderApplication01();


        String previousCoin = "";

        while (true) {
            RestTemplate restTemplate = new RestTemplate();
            String pmDataURL = String.format(
                "https://api.amlvip-in.com/api/Quotes/PmData"
            );
            ResponseEntity<PMDataResponse> pmDataResponse
                = restTemplate.getForEntity(pmDataURL, PMDataResponse.class);
            String coin = pmDataResponse.getBody().Assue;

            if (coin.equals(previousCoin)) {
                Thread.sleep(1000 * 30);
                continue;
            }

            previousCoin = coin;

            RunData runData109 = pmDataResponse.getBody().runData.get(109);
            int resultChannel = 0;
            double maxDist109 = Double.MIN_VALUE;

            for (int index = 0; index < runData109.pmData.size(); index++) {
                if (runData109.pmData.get(index).Dist > maxDist109) {
                    maxDist109 = runData109.pmData.get(index).Dist;
                    resultChannel = runData109.pmData.get(index).Channel;
                }
            }


            boolean skipCoin = false;
            RunData runData108 = pmDataResponse.getBody().runData.get(108);
            for (int index = 0; index < runData108.pmData.size(); index++) {
                if (runData108.pmData.get(index).Channel == resultChannel) {
                    double dist108 = runData108.pmData.get(index).Dist;
                    if (dist108 > maxDist109) {
                        skipCoin = true;
                    }
                }
            }

            if (skipCoin) {
                System.out.println("not sure about result for coin :" + coin);
                Thread.sleep(1000 * 30);
                continue;
            }

            resultChannel = resultChannel + 1;
            System.out.println("sure about result for coin :" + coin + ", result:" + resultChannel);
            long timestamp = System.currentTimeMillis();

            ArrayList<String> responseData = new ArrayList<>();
            for (Map.Entry<Integer, String> userDetail : userDetails.entrySet()) {
                int userId = userDetail.getKey();
                String userPwd = bulkAccountSaveOrderApplication.getMD5(userDetail.getValue());
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
                Thread.sleep(5 * 1000);
            }

            for (String response : responseData) {
                System.out.println(response);
            }
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
