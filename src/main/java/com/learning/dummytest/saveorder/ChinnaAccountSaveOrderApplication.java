package com.learning.dummytest.saveorder;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.RunData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ChinnaAccountSaveOrderApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        int userId = 226416;
        String userPwd = "a3fe2c0b846d21cef58f06284c57f276";
        String orderSecPassword = "902fbdd2b1df0c4f70b4a5d23525e932";
        int orderQuantity = 10000;

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

            String plaintext = String.format("coin=%s&langId=1&order_SecrityPwd=%s&order_expiryTime=1&order_index=%d&order_quantity=%d&order_updown=1&timestamp=%d&userId=%d&userPwd=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
                coin, orderSecPassword, resultChannel, orderQuantity, timestamp, userId, userPwd);
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String sign = bigInt.toString(16);

            String orderSaveUrl = String.format(
                "https://api.amlvip-in.com/api/Quotes/orderSave?coin=%s&userId=%d&userPwd=%s&order_index=%d&order_quantity=%d&order_SecrityPwd=%s&order_updown=1&order_expiryTime=1&langId=1&timestamp=%d&sign=%s",
                coin, userId, userPwd, resultChannel, orderQuantity, orderSecPassword, timestamp, sign
            );

            System.out.println(orderSaveUrl);
            ResponseEntity<String> response
                = restTemplate.postForEntity(orderSaveUrl, null, String.class);
            System.out.println(response.getBody());

        }
    }
}
