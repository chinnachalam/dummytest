package com.learning.dummytest.loadtest;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.RunData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoadTestApplication implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int tid;

    public LoadTestApplication(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        while (true) {
            RestTemplate restTemplate = new RestTemplate();
            String pmDataURL = String.format(
                "https://api.amlvip-in.com/api/Quotes/PmData"
            );
            ResponseEntity<String> pmDataResponse
                = restTemplate.getForEntity(pmDataURL, String.class);
            logger.info(pmDataResponse.getBody());

            String orderSaveUrl = String.format(
                "https://api.amlvip-in.com/api/Quotes/orderSave"
            );

            ResponseEntity<String> response
                = restTemplate.postForEntity(orderSaveUrl, null, String.class);
            logger.info(response.getBody());
        }
    }


    public static void main(String[] args) {
        int threadCount = 10000;
        Thread[] threads = new Thread[threadCount];
        // start threads
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new LoadTestApplication(i));
            threads[i].start();
        }
    }
}
