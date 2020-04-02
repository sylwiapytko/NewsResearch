package com.example.newstest3.service;


import com.example.newstest3.TwitterController.TwitterUserService;
import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Follower;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Log
@Service
public class UserService  {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    TwitterService twitterService;
    
    @Autowired
    TweetService tweetService;

    @Autowired
    TwitterUserService twitterUserService;



    public List<TwitterUser> fetchTwitterAccounts(List<String> accountNames, AccountClassification accountClassificationName){
        List<TwitterUser>  twitterUserList= new ArrayList<>();
        accountNames.stream().map(twitterUserService::fetchTwitterUser).forEach(twitterUserList::add);
        deleteNullUsers(twitterUserList);
        twitterUserList.forEach(twitterUser -> twitterUser.setAccountClassification(accountClassificationName));
        twitterUserList.forEach(twitterUser -> twitterUser.setRetrievedAt(new Date()));
        twitterUserList.forEach(this::fetchTwitterUsersData);

        return twitterUserList;
    }

    private void deleteNullUsers(List<TwitterUser> twitterUserList) {
        twitterUserList.removeAll(Collections.singleton(null));
    }

    public void fetchTwitterUsersData(TwitterUser twitterUser) {
        log.info("User - " + twitterUser.getScreenName());

        twitterUserService.fetchUserFollowers(twitterUser);
        tweetService.fetchTwitterUserTweets(twitterUser);
        saveTwitterUserInfo(twitterUser);
    }


    public TwitterUser saveTwitterUserInfo(TwitterUser twitterUser){

        log.info("Saving - " + twitterUser.getScreenName());
        return userRepository.save(twitterUser);
    }

    public List<TwitterUser> saveTwitterUsersInfo(List<TwitterUser> twitterUsers){
         return userRepository.saveAll(twitterUsers);
    }





    public List<TwitterUser> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
