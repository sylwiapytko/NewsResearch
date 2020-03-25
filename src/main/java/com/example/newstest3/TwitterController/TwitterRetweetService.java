package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.service.RetweeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

@Service
public class TwitterRetweetService {
    
    @Autowired
    Twitter twitter;
    
    @Autowired
    SleepService sleepService;
    
    @Autowired
    RetweeterService retweeterService;

    @Autowired
    TwitterRetweeterService twitterRetweeterService;
    
    
    public Tweet fetchTweetRetweets(Tweet tweet) {
        
        return tweet;
    }


}
