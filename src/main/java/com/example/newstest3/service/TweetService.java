package com.example.newstest3.service;

import com.example.newstest3.TwitterController.TwitterRetweetService;
import com.example.newstest3.TwitterController.TwitterRetweeterService;
import com.example.newstest3.TwitterController.TwitterTweetService;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
@Log
@Service
public class TweetService {


    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TwitterTweetService twitterTweetService;
    @Autowired
    private TwitterRetweetService twitterRetweetService;
    @Autowired
    private TwitterRetweeterService twitterRetweeterService;


    public List<Tweet> fetchTwitterUserTweets(TwitterUser twitterUser){
        twitterTweetService.fetchUserTweets(twitterUser);
        twitterUser.setStatusesFetchedCount();
        twitterUser.getUserTweets().forEach(this::fetchTweetInfo);


     return twitterUser.getUserTweets();
    }

    private void fetchTweetInfo(Tweet tweet) {
        twitterRetweeterService.fetchTweetRetweeters(tweet);//max 75 tweetow for 15 min
    }

    public List<Tweet> saveTwitterUserTweets(TwitterUser twitterUser){
        if (twitterUser.getUserTweets() == null) {
            twitterUser.setUserTweets(new ArrayList<>());
        }
         return tweetRepository.saveAll(twitterUser.getUserTweets());
    }

    public List<Tweet> getTweets() {
        return new ArrayList<>(tweetRepository.findAll());
    }
}
