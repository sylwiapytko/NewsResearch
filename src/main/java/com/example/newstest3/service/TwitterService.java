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

    @Value("#{'${account.names.bigmainstream}'.split(',')}")
    private List<String> accountNamesBigmainstream;


    @Value("#{'${account.names.factcheck}'.split(',')}")
    private List<String> accountNamesFactcheck;

    @Value("#{'${account.names.update}'.split(',')}")
    private List<String> accountNamesUpdate;

    @Autowired
    UserService userService;

    @Autowired
    TwitterService twitterService;


    public void fetchTwitterUsersAccounts(){
        List<TwitterUser> twitterUserListTest = userService.fetchTwitterAccounts(accountNamesTest, AccountClassification.TEST);
        List<TwitterUser> twitterUserListJunk = userService.fetchTwitterAccounts(accountNamesJunk, AccountClassification.JUNK);
        List<TwitterUser> twitterUserListMainstream = userService.fetchTwitterAccounts(accountNamesMainstream, AccountClassification.MAINSTREAM);
        List<TwitterUser> twitterUserListBigMainstream = userService.fetchTwitterAccounts(accountNamesBigmainstream, AccountClassification.BIGMAINSTREAM);
        List<TwitterUser> twitterUserListFactcheck = userService.fetchTwitterAccounts(accountNamesFactcheck, AccountClassification.FACTCHECK);

    }
    public void updateTwitterUsersClassification(){
        List<TwitterUser> twitterUserListUpdate = userService.updateUsersAccountClassification(accountNamesUpdate, AccountClassification.GEMIUS_OCT);

    }
    public void updateTwitterUsersTweets(){
        userService.updateTwitterUsersTweetsbyClassification(AccountClassification.FACTCHECK);
    }
}
