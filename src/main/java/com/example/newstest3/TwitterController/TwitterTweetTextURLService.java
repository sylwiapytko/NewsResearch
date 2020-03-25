package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TweetTextURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.URLEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterTweetTextURLService {

    @Autowired
    Twitter twitter;
    @Autowired
    SleepService sleepService;

    public List<TweetTextURL> fetchURLfromTweet(Status status){
        List<TweetTextURL> tweetTextURLS= new ArrayList<>();
        URLEntity[] urls = status.getURLEntities();
        for(URLEntity urlEntity : urls) {
            TweetTextURL tweetTextURL = new TweetTextURL();
            tweetTextURL.setUrlEntity(urlEntity);
            tweetTextURL.setURL();
            tweetTextURL.setURLExpanded();
            tweetTextURLS.add(tweetTextURL);
        }
        return tweetTextURLS;
    }
}
