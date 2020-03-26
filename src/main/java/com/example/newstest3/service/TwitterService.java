package com.example.newstest3.service;

import com.example.newstest3.entity.TwitterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterService {


    @Value("#{'${usersnames}'.split(',')}")
    private List<String> usersNames;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    public void fetchTwitterUsersAccounts(){
        List<TwitterUser> twitterUserList = userService.fetchTwitterAccounts();
        //twitterUserList.forEach(userService::fetchTwitterUsersData);


        //userService.saveTwitterUsersInfo(twitterUserList);

    }
}
