package com.example.newstest3;

import com.example.newstest3.service.TweetService;
import com.example.newstest3.service.TwitterService;
import com.example.newstest3.service.UserService;
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
        private  String mojparametr;

        @Autowired
        TwitterService twitterService;

        @Autowired
        ApplicationArguments applicationArguments;


        public static void main(String[] args) {
            log.info("STARTING THE APPLICATION");
            SpringApplication.run(Newstest3Application.class, args);
            log.info("APPLICATION FINISHED");
        }

        @Override
        public void run(String... args) {
            log.info("EXECUTING : command line runner");


            String userName = "hildatheseries";
            twitterService.fetchTwitterUsersAccounts();


            for (int i = 0; i < args.length; ++i) {
                log.info(MessageFormat.format("args[{0}]: {1}", i, args[i]));
            }

        }
    }


