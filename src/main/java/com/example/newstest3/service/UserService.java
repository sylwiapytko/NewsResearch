package com.example.newstest3.service;


import com.example.newstest3.TwitterController.TwitterUserService;
import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
import lombok.extern.java.Log;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Log
@Service
@Transactional
public class UserService  {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

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
        twitterUserList.forEach(this::fetchTwitterUsersFollowers);
        twitterUserList.forEach(this::fetchTwitterUsersData);

        return twitterUserList;
    }

    private void deleteNullUsers(List<TwitterUser> twitterUserList) {
        twitterUserList.removeAll(Collections.singleton(null));
    }
    public void fetchTwitterUsersFollowers(TwitterUser twitterUser) {
        log.info("Followers of User - " + twitterUser.getScreenName());

        if(twitterUser.getAccountClassification()!= AccountClassification.BIGMAINSTREAM){
            twitterUserService.fetchUserFollowers(twitterUser);
        }
    }
    @Transactional
    public void fetchTwitterUsersData(TwitterUser twitterUser) {
        log.info("Tweets of User - " + twitterUser.getScreenName());

        tweetService.fetchTwitterUserTweets(twitterUser);
        saveTwitterUserInfo(twitterUser);
    }


    public void updateTwitterUsersTweetsbyClassification(AccountClassification accountClassification){
        List<TwitterUser> twitterUsers = userRepository.findAllByAccountClassificationEquals(accountClassification);
        twitterUsers.forEach(this::initializeTwitterUsersData);
        twitterUsers.forEach(this::fetchTwitterUsersData);
    }

    private void initializeTwitterUsersData(TwitterUser twitterUser) {
       // tweetRepository.findAllByTwitterUserEquals(twitterUser);
        Hibernate.initialize(twitterUser.getUserTweets());
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

    private List<TwitterUser> getUsersbyScreenName(List<String> accountNamesUpdate) {
        List<TwitterUser>  twitterUserList= new ArrayList<>();
        accountNamesUpdate.stream().map(userRepository::findFirstByScreenName).forEach(twitterUserList::add);
        return twitterUserList;
    }

    public  List<TwitterUser> updateUsersAccountClassification(List<String> accountNamesUpdate, AccountClassification accountClassificationName) {

        List<TwitterUser> twitterUsers= getUsersbyScreenName(accountNamesUpdate);
        if(twitterUsers.stream().anyMatch(Objects::isNull)){
            System.out.println(accountClassificationName + " couldnt find some Accounts");
            return null;
        }
        twitterUsers.forEach(twitterUser -> twitterUser.setAccountClassification(accountClassificationName));
        return userRepository.saveAll(twitterUsers);

    }


}
