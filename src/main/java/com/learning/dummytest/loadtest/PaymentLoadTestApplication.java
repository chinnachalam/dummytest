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


public class PaymentLoadTestApplication implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int tid;

    public PaymentLoadTestApplication(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        while (true) {
            try {
                RestTemplate restTemplate = new RestTemplate();

                String paymentSaveURL = String.format(
                    "https://api2.amlvip-in.com/api/Recharge/getResult?payListId=232261&timestamp=1700557130431&sign=cd54d8482dae37c840d99dc874fe741b"
                );
                ResponseEntity<String> paymentSaveResponse
                    = restTemplate.getForEntity(paymentSaveURL, String.class);
                System.out.println(paymentSaveResponse.getStatusCode());
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }


    public static void main(String[] args) {
        int threadCount = 2;
        Thread[] threads = new Thread[threadCount];
        // start threads
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new PaymentLoadTestApplication(i));
            threads[i].start();
        }
    }

}
