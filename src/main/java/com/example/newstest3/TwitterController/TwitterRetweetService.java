package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.service.RetweeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.List;

@Service
public class TwitterRetweetService {
    
    @Autowired
    Twitter twitter;
    
    @Autowired
    SleepService sleepService;

    @Autowired
    StatusToTweet statusToTweet;
    
    @Autowired
    RetweeterService retweeterService;

    @Autowired
    TwitterRetweeterService twitterRetweeterService;
    
    
    public List<Tweet> fetchTweetRetweets(Tweet tweet) {
        try {
            List<Status> statuses = twitter.getRetweets(tweet.getId());
            List<Tweet> retweets = statusToTweet.retriveTweetsfromStatuses(statuses, tweet.getTwitterUser());
            tweet.addtweetRetweets(retweets);
            return retweets;
        } catch (TwitterException e) {
            e.printStackTrace();
            return  null;
        }
    }

}
