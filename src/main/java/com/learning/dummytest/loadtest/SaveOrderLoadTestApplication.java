package com.learning.dummytest.loadtest;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.PMDataResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class SaveOrderLoadTestApplication implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int tid;

    public SaveOrderLoadTestApplication(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        while (true) {
            try {
                RestTemplate restTemplate = new RestTemplate();

                try {
                    String pmDataURL = String.format(
                        "https://api2.amlvip-in.com/api/Quotes/PmData"
                    );
                    ResponseEntity<PMDataResponse> pmDataResponse
                        = restTemplate.getForEntity(pmDataURL, PMDataResponse.class);
                    String pmDataResponseCoin = pmDataResponse.getBody().Assue;
                    System.out.println(pmDataResponseCoin);
                } catch (Exception e) {
                    System.out.println("pmDataURL error:" + e.getMessage());
                }
                try {
                    String pmDataURL1 = String.format(
                        "https://api.amlvip-in.com/api/Quotes/PmData"
                    );
                    ResponseEntity<PMDataResponse> pmDataResponse1
                        = restTemplate.getForEntity(pmDataURL1, PMDataResponse.class);
                    String pmDataResponseCoin1 = pmDataResponse1.getBody().Assue;
                    System.out.println(pmDataResponseCoin1);
                } catch (Exception e) {
                    System.out.println("pmDataURL1 error:" + e.getMessage());
                }
                try {
                    String pmDataResultURL = String.format(
                        "https://api2.amlvip-in.com/api/Quotes/PmDataResult"
                    );

                    ResponseEntity<PMDataResultResponse> pmDataResultResponse
                        = restTemplate.getForEntity(pmDataResultURL, PMDataResultResponse.class);
                    String resultCoin = pmDataResultResponse.getBody().Assue;
                    System.out.println(resultCoin);
                } catch (Exception e) {
                    System.out.println("pmDataResultURL error:" + e.getMessage());
                }
                try {
                    String pmDataResultURL1 = String.format(
                        "https://api.amlvip-in.com/api/Quotes/PmDataResult"
                    );

                    ResponseEntity<PMDataResultResponse> pmDataResultResponse1
                        = restTemplate.getForEntity(pmDataResultURL1, PMDataResultResponse.class);
                    String resultCoin1 = pmDataResultResponse1.getBody().Assue;
                    System.out.println(resultCoin1);
                } catch (Exception e) {
                    System.out.println("pmDataResultURL1 error:" + e.getMessage());
                }

                try {
                    String paymentSaveURL = String.format(
                        "https://api2.amlvip-in.com/api/Recharge/getResult?payListId=232261&timestamp=1700557130431&sign=cd54d8482dae37c840d99dc874fe741b"
                    );
                    ResponseEntity<String> paymentSaveResponse
                        = restTemplate.getForEntity(paymentSaveURL, String.class);
                    System.out.println("paymentSaveURL success:" + paymentSaveResponse.getStatusCode());
                } catch (Exception e) {
                    System.out.println("paymentSaveURL error:" + e.getMessage());
                }

                int userId = 135743;
                String userPwd = "31632dd470561e3c9af75914ad868ed0";
                String orderSecPassword = "902fbdd2b1df0c4f70b4a5d23525e932";
                long timestamp = System.currentTimeMillis();
                String coin = "20231119300"; //TODO
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

                try {
                    String orderSaveUrl = String.format(
                        "https://api.amlvip-in.com/api/Quotes/orderSave?coin=%s&userId=%d&userPwd=%s&order_index=%d&order_quantity=%d&order_SecrityPwd=%s&order_updown=1&order_expiryTime=1&langId=1&timestamp=%d&sign=%s",
                        coin, userId, userPwd, resultChannel, orderQuantity, orderSecPassword, timestamp, sign
                    );
                    ResponseEntity<String> response
                        = restTemplate.postForEntity(orderSaveUrl, null, String.class);
                    System.out.println("orderSaveUrl success:" +response.getBody());
                } catch (Exception e) {
                    System.out.println("orderSaveUrl error:" + e.getMessage());
                }
                try {
                    String orderSaveUrl1 = String.format(
                        "https://api2.amlvip-in.com/api/Quotes/orderSave?coin=%s&userId=%d&userPwd=%s&order_index=%d&order_quantity=%d&order_SecrityPwd=%s&order_updown=1&order_expiryTime=1&langId=1&timestamp=%d&sign=%s",
                        coin, userId, userPwd, resultChannel, orderQuantity, orderSecPassword, timestamp, sign
                    );
                    ResponseEntity<String> response1
                        = restTemplate.postForEntity(orderSaveUrl1, null, String.class);
                    System.out.println("orderSaveUrl1 success:" + response1.getBody());
                } catch (Exception e) {
                    System.out.println("orderSaveUrl1 error:" + e.getMessage());
                }

                try {
                    String withdrawURL = String.format(
                        "https://api2.amlvip-in.com/api/Withdraw/Save?" +
                            "userId=135743&Quantity=44000&userPwd=1d19cdcbb493a129006a6cdb791ff166&langId=1&timestamp=1700021728949&sign=065c38c15e1df054674d99ce02fedb06"
                    );
                    ResponseEntity<PMDataResultResponse> withDrawResponse
                        = restTemplate.getForEntity(withdrawURL, PMDataResultResponse.class);
                    System.out.println("withdrawURL success:" + withDrawResponse.getStatusCode());
                } catch (Exception e) {
                    System.out.println("withdrawURL error:" + e.getMessage());
                }
                try {
                    String withdrawURL1 = String.format(
                        "https://api.amlvip-in.com/api/Withdraw/Save?" +
                            "userId=135743&Quantity=44000&userPwd=1d19cdcbb493a129006a6cdb791ff166&langId=1&timestamp=1700021728949&sign=065c38c15e1df054674d99ce02fedb06"
                    );
                    ResponseEntity<PMDataResultResponse> withDrawResponse1
                        = restTemplate.getForEntity(withdrawURL1, PMDataResultResponse.class);
                    System.out.println("withdrawURL1 success:" + withDrawResponse1.getStatusCode());
                } catch (Exception e) {
                    System.out.println("withdrawURL1 error:" + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("general error" + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        int threadCount = 2;
        Thread[] threads = new Thread[threadCount];
        // start threads
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new SaveOrderLoadTestApplication(i));
            threads[i].start();
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

    public static String getPhoneNumber() {
        int num1, num2, num3; //3 numbers in area code
        int set2, set3; //sequence 2 and 3 of the phone number

        Random generator = new Random();

        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin
        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
        num3 = generator.nextInt(8);

        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742
        set2 = generator.nextInt(643) + 100;

        //Sequence 3 of numebr
        // add 1000 so there will always be 4 numbers
        //8999 so it wont succed 9999 when the 1000 is added
        set3 = generator.nextInt(8999) + 1000;

        return num1 + "" + num2 + "" + num3 + "" + set2 + "" + set3;
    }
}
