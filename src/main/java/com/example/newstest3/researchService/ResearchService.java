package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.repository.RetweeterRepository;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.UserRepository;
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

    @Value("${date.retweeters.start}")
    private String retweetersDateStart;

    @Value("${date.retweeters.end}")
    private String retweetersDateEnd;

    public void writeTweetswithRetweetersToJson(){
        Map<String, List<String>> usersRetweetersMap  = new HashMap<>();
        usersRetweetersMap.putAll(getUserTweetswithRetweeters(AccountClassification.JUNK));
        usersRetweetersMap.putAll(getUserTweetswithRetweeters(AccountClassification.MAINSTREAM));

        writeMapRetweeterstoJson(usersRetweetersMap);
    }

    private Map<String,List<String>> getUserTweetswithRetweeters(AccountClassification accountClassification) {
        List<Tweet> tweetsList = tweetRepository.findTweetsByAccountClassification(accountClassification, utils.formatStringtoDate(retweetersDateStart), utils.formatStringtoDate(retweetersDateEnd));
        System.out.println(tweetsList.size());
        ///tweetsList.forEach(this::initializeTwitterUsersData);
        System.out.println("finished initializing " + tweetsList.size());
        return createMap(tweetsList);
    }

    private Map<String, List<String>> createMap(List<Tweet> tweetsList) {
        Map<String, List<String>> retweetersMap = new HashMap<>();
        for (Tweet tweet : tweetsList) {
            String userTweetId = new StringBuilder().append(Long.toString(tweet.getTwitterUser().getId())).append("_").append(Long.toString(tweet.getId())).toString();
            retweetersMap.put(userTweetId, getTweetsRetweeters(tweet.getId()));
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
