package com.example.newstest3.researchService;

import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
import com.example.newstest3.repository.UserTweetRetweeterRepository;
import com.example.newstest3.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class NetworkService {
    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTweetRetweeterRepository userTweetRetweeterRepository;

    @Value("${date.retweeters.start}")
    private String retweetersDateStart;

    @Value("${date.retweeters.end}")
    private String retweetersDateEnd;

}
