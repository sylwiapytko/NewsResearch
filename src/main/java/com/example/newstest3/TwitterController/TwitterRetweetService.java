package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.service.RetweeterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
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
            List<Tweet> retweets = retriveTweetsfromStatuses(statuses);
            tweet.addtweetRetweets(retweets);
            return retweets;
        } catch (TwitterException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public List<Tweet> retriveTweetsfromStatuses(List<Status> statusesNew) {
        List<Tweet> tweets = new ArrayList<>();
        for(Status status: statusesNew){
            tweets.add(retriveTweetfromStatus(status));
        }
        return tweets;
    }

    private Tweet retriveTweetfromStatus(Status status) {
        Tweet tweet = new Tweet();
        BeanUtils.copyProperties(status,tweet);
        tweet.setTwitterUserScreenName(status.getUser().getScreenName());
        tweet.setTwitterUser(retriveUserfromStatus(status));
        return tweet;
    }

    private TwitterUser retriveUserfromStatus(Status status) {
        TwitterUser twitterUser = new TwitterUser();
        BeanUtils.copyProperties(status.getUser(),twitterUser);
        return  twitterUser;
    }

    //TODO saving the Users

}
