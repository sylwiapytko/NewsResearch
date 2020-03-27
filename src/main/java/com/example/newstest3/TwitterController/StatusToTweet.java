package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusToTweet {

    @Autowired
    Twitter twitter;

    @Autowired
    SleepService sleepService;

    @Autowired
    TwitterTweetTextHashtagService twitterTweetTextHashtagService;

    @Autowired
    TwitterTweetTextURLService twitterTweetTextURLService;


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
        retriveTweetExtraInfo(status,tweet);

        return tweet;
    }

    private Tweet retriveTweetExtraInfo(Status status, Tweet tweet) {
        tweet.setTextLength();
        tweet.addtweetTextHashtags(twitterTweetTextHashtagService.fetchHashtagsfromTweet(status));
        tweet.addtweetTextURL(twitterTweetTextURLService.fetchURLfromTweet(status));
        return tweet;
    }
}
