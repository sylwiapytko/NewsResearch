package com.example.newstest3;

import com.example.newstest3.TwitterController.AppTwitterControler;
import com.example.newstest3.controller.AppController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@SpringBootApplication
public class Newstest3Application {

    public static void main(String[] args) throws TwitterException {
        SpringApplication.run(Newstest3Application.class, args);

        AppController appController = new AppController();
        //appController.callCreateUsers();
        //appController.callGetUsers();
        AppTwitterControler appTwitterControler= new AppTwitterControler();
        appTwitterControler.testTwitterController();


    }


}
