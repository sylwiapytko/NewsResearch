package com.example.newstest3;

import com.example.newstest3.TwitterController.TwitterService;
import com.example.newstest3.service.TweetService;
import com.example.newstest3.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import twitter4j.TwitterException;

import java.text.MessageFormat;

    @Log
    @SpringBootApplication
    public class Newstest3Application
            implements CommandLineRunner {

        @Value("${mojparametr}")
        private  String mojparametr;

        @Autowired
        UserService userService;
        @Autowired
        TweetService tweetService;

        @Autowired
        ApplicationArguments applicationArguments;

        @Autowired
        TwitterService twitterService;

        public static void main(String[] args) {
            log.info("STARTING THE APPLICATION");
            SpringApplication.run(Newstest3Application.class, args);
            log.info("APPLICATION FINISHED");
        }

        @Override
        public void run(String... args) throws TwitterException {
            log.info("EXECUTING : command line runner");


            String userName = "hildatheseries";
            userService.fetchTwitterUsers();
            // userService.getUsers().forEach(user -> log.info(user.toString()));
            // userService.getUsers().forEach(user -> log.info(user.getURLEntity().getExpandedURL()));
            //userService.getUsers().forEach(System.out::println);
            tweetService.fetchTwitterUsersTweets();

            for (int i = 0; i < args.length; ++i) {
                log.info(MessageFormat.format("args[{0}]: {1}", i, args[i]));
            }

            twitterService.printUserTimeline(userName, 1, 2);
        }
    }


