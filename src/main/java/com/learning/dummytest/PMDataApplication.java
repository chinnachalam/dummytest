package com.learning.dummytest;

import com.opencsv.CSVWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class PMDataApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException, IOException {
        File file = new File("C:\\dev\\workspace\\dummytest\\src\\main\\resources\\data2.csv");
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);

        while (true) {
            RestTemplate restTemplate = new RestTemplate();
            String pmDataURL = String.format(
                "https://api.amlvip-in.com/api/Quotes/PmData"
            );
            ResponseEntity<PMDataResponse> pmDataResponse
                = restTemplate.getForEntity(pmDataURL, PMDataResponse.class);
            String coin = pmDataResponse.getBody().Assue;

            while (true) {
                String pmDataResultURL = String.format(
                    "https://api.amlvip-in.com/api/Quotes/PmDataResult"
                );
                ResponseEntity<PMDataResultResponse> pmDataResultResponse
                    = restTemplate.getForEntity(pmDataResultURL, PMDataResultResponse.class);
                String resultCoin = pmDataResultResponse.getBody().Assue;

                if (coin.equals(resultCoin)) {
                    if (pmDataResultResponse.getBody().runData != null && pmDataResultResponse.getBody().runData.size() > 0) {
                        /*RunData lastRunData = pmDataResultResponse.getBody().runData.get(pmDataResultResponse.getBody().runData.size() - 1);
                        int resultChannel = 0;
                        double maxDist = Double.MIN_VALUE;
                        System.out.println(lastRunData.runTime);
                        for (int index = 0; index < lastRunData.pmData.size(); index++) {
                            if (lastRunData.pmData.get(index).Dist > maxDist) {
                                maxDist = lastRunData.pmData.get(index).Dist;
                                resultChannel = lastRunData.pmData.get(index).Channel;
                            }
                        }
                        resultChannel = resultChannel + 1;
                        System.out.println("resultChannel:" + resultChannel);*/



                        // create a List which contains String array
                        List<String[]> data = new ArrayList<>();

                        System.out.println("***** WRITING DATA TO FILE START ****");
                        pmDataResponse.getBody().runData.forEach(runData -> {
                            runData.pmData.forEach(pmData -> {
                                data.add(new String[]{"PMData", coin, String.valueOf(runData.runTime), String.valueOf(pmData.Channel), String.valueOf(pmData.Dist)});
                            });
                        });
                        pmDataResultResponse.getBody().runData.forEach(runData -> {
                            runData.pmData.forEach(pmData -> {
                                data.add(new String[]{"PMDataResult", coin, String.valueOf(runData.runTime), String.valueOf(pmData.Channel), String.valueOf(pmData.Dist)});
                            });
                        });
                        System.out.println("***** WRITING DATA TO FILE END ****");

                        writer.writeAll(data);

                        break;
                    }
                } else {
                    break;
                }
                Thread.sleep(5000);
            }
            Thread.sleep(120000);
        }
    }
}
