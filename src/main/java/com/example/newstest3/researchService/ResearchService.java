package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.model.UserTweetsRetweeters;
import com.example.newstest3.repository.RetweeterRepository;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
import com.example.newstest3.repository.UserTweetRetweeterRepository;
import com.example.newstest3.service.TweetService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class ResearchService {



    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;
    @Autowired
    RetweeterRepository retweeterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserTweetRetweeterRepository userTweetRetweeterRepository;

    @Value("${date.retweeters.start}")
    private String retweetersDateStart;

    @Value("${date.retweeters.end}")
    private String retweetersDateEnd;

    public void writeTweetswithRetweetersToJson(){
        Map<String, List<String>> usersRetweetersMap  = new HashMap<>();
        usersRetweetersMap.putAll(getUserTweetswithRetweeters(AccountClassification.JUNK));
        usersRetweetersMap.putAll(getUserTweetswithRetweeters(AccountClassification.MAINSTREAM));
        usersRetweetersMap.putAll(getUserTweetswithRetweeters(AccountClassification.BIGMAINSTREAM));

        writeMapRetweeterstoJson(usersRetweetersMap);
    }

    private Map<String,List<String>> getUserTweetswithRetweeters(AccountClassification accountClassification) {
        List<TwitterUser> twitterUserList =userRepository.findAllByAccountClassificationEquals(accountClassification);
        System.out.println("num of users : "+ twitterUserList.size());
        Map<String, List<String>> retweetersMap = new HashMap<>();
        for(TwitterUser twitterUser: twitterUserList){
            long start = System.currentTimeMillis();
            retweetersMap.putAll(getUserTweetsRetweeters(twitterUser));

            long elapsedTime2 = System.currentTimeMillis() - start;
            System.out.println(" put to map user s: " + elapsedTime2/1000);
        }

        return retweetersMap;
    }

    private Map<String, List<String>> getUserTweetsRetweeters(TwitterUser twitterUser) {
        List<Long> tweetsList = tweetRepository.findAllOriginalWithRetweetersByTwitterUserAndTime(twitterUser.getId(), utils.formatStringtoDate(retweetersDateStart), utils.formatStringtoDate(retweetersDateEnd));
        List<Retweeter> retweeterList = retweeterRepository.findRetweetersByTweetIdInList(tweetsList);
        //List<UserTweetsRetweeters> userTweetsRetweetersList = userTweetRetweeterRepository.findRetweetersByUserId(twitterUser.getId(), utils.formatStringtoDate(retweetersDateStart), utils.formatStringtoDate(retweetersDateEnd));
//        Map<Tweet, Long> retweeterMap = retweeterList.stream()
//                .collect(Collectors.toMap(Retweeter::getTweet,Retweeter::getRetweeterId));
        System.out.println(twitterUser.getScreenName() + "num of tweets : "+ tweetsList.size());
        return createMap(tweetsList, Long.toString(twitterUser.getId()), retweeterList);
    }

//    private Map<TwitterUser, String> createUserIdMap(AccountClassification accountClassification) {
//        List<TwitterUser> twitterUserList =userRepository.findAllByAccountClassificationEquals(accountClassification);
//        Map<TwitterUser, String> usersIdMap = new HashMap<>();
//        for (TwitterUser twitterUser : twitterUserList) {
//
//            usersIdMap.put(twitterUser,Long.toString(twitterUser.getId()) );
//        }
//        return usersIdMap;
//    }

    private Map<String, List<String>> createMap(List<Long> tweetIdList,  String userId,  List<Retweeter> retweeterList) {
        Map<String, List<String>> retweetersMap = new HashMap<>();

        for (Long tweetId : tweetIdList) {
            String userTweetId = new StringBuilder().append(userId).append("_").append(Long.toString(tweetId)).toString();
            retweetersMap.put(userTweetId, getTweetsRetweeters(tweetId));
        }
        return  retweetersMap;
    }

    private void initializeTwitterUsersData(Tweet tweet) {
        Hibernate.initialize(tweet.getTweetTextHashtags());
        Hibernate.initialize(tweet.getTweetTextURLS());
        Hibernate.initialize(tweet.getTweetRetweeters());
        Hibernate.initialize(tweet.getTweetRetweets());
        System.out.print(".");
    }


    private List<String> getTweetsRetweeters(Long tweetId) {
        return retweeterRepository.findRetweetersByTweetId(tweetId)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }


    private void writeMapRetweeterstoJson(Map<String,  List<String>> usersRetweetersMap) {
        try (Writer writer = new FileWriter("retweeters.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(usersRetweetersMap, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveClassificationAccountsToJson(AccountClassification accountClassification){
        List<String> twitterUserList = userRepository.findIdsByAccountClassificationEquals(accountClassification).stream()
                .map(Object::toString)
                .collect(Collectors.toList());;

        saveListtoJson(twitterUserList, accountClassification);
    }

    private void saveListtoJson(List<String> classUsersList , AccountClassification accountClassification) {
        try (Writer writer = new FileWriter(accountClassification.toString() + ".json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(classUsersList, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveAccountswithClassificationToJson(){
        Map<String, Boolean> userClassificationMap  = new HashMap<>();
        userClassificationMap.putAll(getUserswithClassification(AccountClassification.JUNK, true));
        userClassificationMap.putAll(getUserswithClassification(AccountClassification.MAINSTREAM, false));
        userClassificationMap.putAll(getUserswithClassification(AccountClassification.BIGMAINSTREAM, false));

        saveMaptoJson(userClassificationMap);
    }

    private void saveMaptoJson(Map<String, Boolean> userClassificationMap) {
        try (Writer writer = new FileWriter("accountClassification.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(userClassificationMap, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, Boolean> getUserswithClassification(AccountClassification accountClassification, Boolean ifJunk) {
        List<Long> twitterUserList = userRepository.findIdsByAccountClassificationEquals(accountClassification);

        Map<String, Boolean> userClassificationMap = new HashMap<>();
        for (Long userId : twitterUserList) {
            userClassificationMap.put(Long.toString(userId), ifJunk);
        }
        return  userClassificationMap;
    }



}
