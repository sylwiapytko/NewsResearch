package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.service.RetweeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
