package com.example.newstest3.TwitterController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Configuration
public class TwitterConfig {
    @Bean
    public Twitter twitter(){
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;
    }
}
