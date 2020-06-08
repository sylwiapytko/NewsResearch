package com.example.newstest3;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.researchService.ExtraDataService;
import com.example.newstest3.researchService.NetworkService;
import com.example.newstest3.researchService.ResearchService;
import com.example.newstest3.service.TweetService;
import com.example.newstest3.service.TwitterService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;

@Log
@SpringBootApplication
public class Newstest3Application
        implements CommandLineRunner {

    @Value("${mojparametr}")
    private String mojparametr;

    @Value("${date.lastFetch}")
    private String lastFetchDate;
    @Value("${date.dummy}")
    private String dummyDate;

    @Autowired
    TwitterService twitterService;

    @Autowired
    TweetService tweetService;

    @Autowired
    ExtraDataService extraDataService;

    @Autowired
    ResearchService researchService;

    @Autowired
    ApplicationArguments applicationArguments;

    @Autowired
    NetworkService networkService;


    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(Newstest3Application.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        log.info("RUN");

        //twitterService.fetchTwitterUsersAccounts();
        //twitterService.updateTwitterUsersTweets();
        //extraDataService.fetchAGAINRetweetersofUsersbyClassificationandTime();

        //extraDataService.fetchRetweetersofUsersbyClassificationandTime();
        //researchService.saveAccountswithClassificationToJson();
       // researchService.writeTweetswithRetweetersToJson();
       // researchService.saveClassificationAccountsToJson(AccountClassification.JUNK);

        networkService.fetchGraphEdges();


    }


}


