package com.example.newstest3.service;

import com.example.newstest3.TwitterController.TwitterTweetTextURLService;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TweetTextURL;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetTextURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TweetTextURLService {

    @Autowired
    TwitterTweetTextURLService twitterTweetTextURLService;

    @Autowired
    TweetTextURLRepository tweetTextURLRepository;

    public void fetchTweetTextURLS(Tweet tweet){
        //TODO: double urls are saved do DB
        twitterTweetTextURLService.fetchURLfromTweet(tweet);
        saveTweetTextURLS(tweet);
    }

    public void saveTweetTextURLS(Tweet tweet){

        if (tweet.getTweetTextURLS() == null) {
            tweet.setTweetTextURLS(new ArrayList<>());
        }
        tweetTextURLRepository.saveAll(tweet.getTweetTextURLS());
    }


}
