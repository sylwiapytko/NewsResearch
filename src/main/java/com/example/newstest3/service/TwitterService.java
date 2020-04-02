package com.example.newstest3.service;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.TwitterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterService {

    @Value("#{'${account.names.test}'.split(',')}")
    private List<String> accountNamesTest;

    @Value("#{'${account.names.junk}'.split(',')}")
    private List<String> accountNamesJunk;

    @Value("#{'${account.names.mainstream}'.split(',')}")
    private List<String> accountNamesMainstream;

    @Value("#{'${account.names.factcheck}'.split(',')}")
    private List<String> accountNamesFactcheck;

    @Autowired
    UserService userService;


    public void fetchTwitterUsersAccounts(){
        List<TwitterUser> twitterUserListTest = userService.fetchTwitterAccounts(accountNamesTest, AccountClassification.TEST);
        List<TwitterUser> twitterUserListJunk = userService.fetchTwitterAccounts(accountNamesJunk, AccountClassification.JUNK);
        List<TwitterUser> twitterUserListMainstream = userService.fetchTwitterAccounts(accountNamesMainstream, AccountClassification.MAINSTREAM);
        List<TwitterUser> twitterUserListFactcheck = userService.fetchTwitterAccounts(accountNamesFactcheck, AccountClassification.FACTCHECK);


    }
}
