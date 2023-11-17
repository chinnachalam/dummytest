package com.learning.dummytest.loadtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;


public class RegisterLoadTestApplication implements Runnable {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private int tid;

    public RegisterLoadTestApplication(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        while (true) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                int desiredLength = 6;
                String username = UUID.randomUUID()
                    .toString()
                    .substring(0, desiredLength);
                String phoneNumber = getPhoneNumber();
                String signedPassword = getMD5(phoneNumber);
                String email = phoneNumber + "@gmail.com";
                String parentID = "449865";
                long timestamp = System.currentTimeMillis();

                String plaintext = String.format(
                    "Email=%s&Phone=%s&langId=1&parentId=%s&timestamp=%d&userName=%s&userPwd=%s&key=19076bcd2fba4a1e2c5aba0b7497e06e",
                    email, phoneNumber, parentID, timestamp, username, signedPassword);
                String sign = getMD5(plaintext);

                String registerUrl = String.format(
                    "https://api.amlvip-in.com/api/Reg/doReg" +
                        "?Email=%s&Phone=%s&langId=1&parentId=%s&timestamp=%d&userName=%s&userPwd=%s&sign=%s",
                    email, phoneNumber, parentID, timestamp, username, signedPassword, sign
                );
                ResponseEntity<String> registerResponse
                    = restTemplate.getForEntity(registerUrl, String.class);
                System.out.println(registerResponse);
                Thread.sleep(5 * 1000);
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }


    public static void main(String[] args) {
        int threadCount = 10000;
        Thread[] threads = new Thread[threadCount];
        // start threads
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new RegisterLoadTestApplication(i));
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
