package com.example.newstest3.service;

import com.example.newstest3.TwitterController.TwitterTweetService;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TweetService {

    @Value("#{'${usersnames}'.split(',')}")
    private List<String> usersNames;

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TwitterTweetService twitterTweetService;

    public  void  fetchTwitterUsersTweets(){
        usersNames.stream().forEach(twitterTweetService::getUserTimelineList);
    }

    public List<Tweet> getUsers() {
        return new ArrayList<>(tweetRepository.findAll());
    }
}
