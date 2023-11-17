package com.learning.dummytest.runner;

import com.learning.dummytest.PMDataResponse;
import com.learning.dummytest.PMDataResultResponse;
import com.learning.dummytest.RunData;
import com.learning.dummytest.loadtest.RegisterLoadTestApplication;
import com.learning.dummytest.login.RegisterApplication;
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

/*    @Autowired
    private PMDataRepository pmDataRepository;
    @Autowired
    private PMDataAnalyzeRepository pmDataAnalyzeRepository;*/


    @Override
    public void run(String... args) throws Exception {
        int threadCount = 100;
        Thread[] threads = new Thread[threadCount];
        // start threads
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new RegisterLoadTestApplication(i));
            threads[i].start();
        }
    }
}
