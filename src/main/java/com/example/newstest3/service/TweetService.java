package com.example.newstest3.service;

import com.example.newstest3.TwitterController.TwitterTweetService;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;
import java.text.MessageFormat;

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
    private TweetTextURLService tweetTextURLService;

    public void fetchTwitterUserTweets(TwitterUser twitterUser){
        twitterTweetService.fetchUserTweets(twitterUser);
        twitterUser.setStatusesFetchedCount();
        saveTwitterUserTweets(twitterUser);
        twitterUser.getUserTweets().forEach(tweetTextURLService::fetchTweetTextURLS);
    }

    public void saveTwitterUserTweets(TwitterUser twitterUser){
        if (twitterUser.getUserTweets() == null) {
            twitterUser.setUserTweets(new ArrayList<>());
        }
        tweetRepository.saveAll(twitterUser.getUserTweets());
    }

    public List<Tweet> getUsers() {
        return new ArrayList<>(tweetRepository.findAll());
    }
}
