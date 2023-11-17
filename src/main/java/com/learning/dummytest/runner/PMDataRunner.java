package com.learning.dummytest.runner;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.PMDataResultResponse;
import com.learning.dummytest.RunData;
import com.learning.dummytest.model.PMdata;
import com.learning.dummytest.model.PMdataAnalize;
import com.learning.dummytest.repository.PMDataAnalyzeRepository;
import com.learning.dummytest.repository.PMDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class PMDataRunner implements CommandLineRunner {

    @Autowired
    private PMDataRepository pmDataRepository;
    @Autowired
    private PMDataAnalyzeRepository pmDataAnalyzeRepository;


    @Override
    public void run(String... args) throws Exception {
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
                        List<PMdata> pmDataList = new ArrayList<>();

                        System.out.println("***** WRITING DATA TO PMDATA TABLE START ****");
                        RunData runData109 = pmDataResponse.getBody().runData.get(109);
                        int runData109Channel = 0;
                        double runData109MaxDist = Double.MIN_VALUE;
                        for (int index = 0; index < runData109.pmData.size(); index++) {
                            if (runData109.pmData.get(index).Dist > runData109MaxDist) {
                                runData109MaxDist = runData109.pmData.get(index).Dist;
                                runData109Channel = runData109.pmData.get(index).Channel;
                            }
                        }

                        RunData runDataResult119 = pmDataResultResponse.getBody().runData.get(119);
                        int runDataResult119Channel = 0;
                        double runDataResult119MaxDist = Double.MIN_VALUE;

                        for (int index = 0; index < runDataResult119.pmData.size(); index++) {
                            if (runDataResult119.pmData.get(index).Dist > runDataResult119MaxDist) {
                                runDataResult119MaxDist = runDataResult119.pmData.get(index).Dist;
                                runDataResult119Channel = runDataResult119.pmData.get(index).Channel;
                            }
                        }

                        String result = runData109Channel == runDataResult119Channel ? "same": "not_same";

                        runData109.pmData.forEach(pmData -> {
                            PMdata pMdata = PMdata.builder()
                                .type("Data")
                                .coin(coin)
                                .runTime(String.valueOf(runData109.runTime))
                                .channel(String.valueOf(pmData.Channel))
                                .dist(String.valueOf(pmData.Dist))
                                .result(result)
                                .build();
                            pmDataList.add(pMdata);
                        });

                        RunData runDataResult109 = pmDataResultResponse.getBody().runData.get(109);
                        runDataResult109.pmData.forEach(pmData -> {
                            PMdata pMdata = PMdata.builder()
                                .type("DataResult")
                                .coin(coin)
                                .runTime(String.valueOf(runDataResult109.runTime))
                                .channel(String.valueOf(pmData.Channel))
                                .dist(String.valueOf(pmData.Dist))
                                .result(result)
                                .build();
                            pmDataList.add(pMdata);
                        });

                        runDataResult119.pmData.forEach(pmData -> {
                            PMdata pMdata = PMdata.builder()
                                .type("DataResult")
                                .coin(coin)
                                .runTime(String.valueOf(runDataResult119.runTime))
                                .channel(String.valueOf(pmData.Channel))
                                .dist(String.valueOf(pmData.Dist))
                                .result(result)
                                .build();
                            pmDataList.add(pMdata);
                        });

                        pmDataRepository.saveAll(pmDataList);
                        System.out.println("***** WRITING DATA TO PMDATA TABLE END ****");

                        if(runData109Channel != runDataResult119Channel) {
                            List<PMdataAnalize> pmDataAnalyzeList = new ArrayList<>();
                            System.out.println("result is not correct for coin:" + coin);
                            System.out.println("***** WRITING DATA TO PMDATA_ANALYZE TABLE START ****");
                            pmDataResponse.getBody().runData.forEach(runData -> {
                                runData.pmData.forEach(pmData -> {
                                    PMdataAnalize pmdataAnalize = PMdataAnalize.builder()
                                        .type("PMData")
                                        .coin(coin)
                                        .runTime(String.valueOf(runData.runTime))
                                        .channel(String.valueOf(pmData.Channel))
                                        .dist(String.valueOf(pmData.Dist))
                                        .build();
                                    pmDataAnalyzeList.add(pmdataAnalize);
                                });
                            });

                            pmDataResultResponse.getBody().runData.forEach(runData -> {
                                runData.pmData.forEach(pmData -> {
                                    PMdataAnalize pmdataAnalize = PMdataAnalize.builder()
                                        .type("PMDataResult")
                                        .coin(coin)
                                        .runTime(String.valueOf(runData.runTime))
                                        .channel(String.valueOf(pmData.Channel))
                                        .dist(String.valueOf(pmData.Dist))
                                        .build();
                                    pmDataAnalyzeList.add(pmdataAnalize);
                                });
                            });
                            pmDataAnalyzeRepository.saveAll(pmDataAnalyzeList);
                            System.out.println("***** WRITING DATA TO PMDATA_ANALYZE TABLE END ****");
                        }

                        break;
                    }
                } else {
                    break;
                }
                Thread.sleep(5000);
            }
            Thread.sleep(60000);
        }

    }
}
