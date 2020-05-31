package com.example.newstest3.service;

import com.example.newstest3.TwitterController.TwitterRetweetService;
import com.example.newstest3.TwitterController.TwitterRetweeterService;
import com.example.newstest3.TwitterController.TwitterTweetService;
import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log
@Service
public class TweetService {

    @Value("${date.retweeters.start}")
    private String retweetersDateStart;

    @Value("${date.retweeters.end}")
    private String retweetersDateEnd;

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TwitterTweetService twitterTweetService;
    @Autowired
    private TwitterRetweetService twitterRetweetService;
    @Autowired
    private TwitterRetweeterService twitterRetweeterService;

    int counter = 0;
    int counterTotal = 0;

    public List<Tweet> fetchTwitterUserTweets(TwitterUser twitterUser) {
        twitterTweetService.fetchUserTweets(twitterUser);
        twitterUser.setStatusesFetchedCount();

        return twitterUser.getUserTweets();
    }

    private void fetchRetweetInfo(Tweet tweet) {


        twitterRetweeterService.fetchTweetRetweeters(tweet);//max 75 tweetow for 15 min
        //twitterRetweetService.fetchTweetRetweets(tweet);//max 75 tweetow for 15 min
        System.out.println("tweet " + tweet.getCreatedAt() + " : "+ tweet.getId()+ "  counter " + ++counter + ": total " + tweet.getRetweetCount() + " retreivedCount: " + tweet.getRetweetedFetchedRetweetersCount() + " retreived size " + tweet.getTweetRetweeters().size() );

    }


    @Transactional
    public List<Tweet> fetchLostRetweetersofUsersbyClassificationandTime(AccountClassification accountClassification
            , Date dateStart, Date dateEnd) {

        List<Tweet> tweetList = tweetRepository.findTweetsByAccountClassificationAndTimeParamsAndNoRetweetersReallyFetched(accountClassification, dateStart, dateEnd);

        return fetchRetweeters(accountClassification, dateStart, dateEnd, tweetList);
    }

    @Transactional
    public List<Tweet> fetchRetweetersofUsersbyClassificationandTime(AccountClassification accountClassification
            , Date dateStart, Date dateEnd) {

        List<Tweet> tweetList = tweetRepository.findTweetsByAccountClassificationAndTimeParamsAndNoRetweetersReallyFetched(accountClassification, dateStart, dateEnd);

        return  fetchRetweeters(accountClassification, dateStart, dateEnd, tweetList);

    }


    private List<Tweet> fetchRetweeters(AccountClassification accountClassification, Date dateStart, Date dateEnd, List<Tweet> tweetList) {
        counter = 0;
        counterTotal += tweetList.size();
        System.out.println(accountClassification + " day: " + dateStart + " - " + dateEnd + "  counter " + counter + " / " + tweetList.size());

        tweetList.forEach(this::initializeTwitterUsersData);
        tweetList.forEach(this::fetchRetweetInfo);


        System.out.println(accountClassification + " day: " + dateStart + " - " + dateEnd + "  counter " + counter + " / " + tweetList.size());
        System.out.println("tweets count " + tweetList.size());
        System.out.println("tweets count total " + counterTotal);

        return tweetList;
    }


    private void initializeTwitterUsersData(Tweet tweet) {
        Hibernate.initialize(tweet.getTweetTextHashtags());
        Hibernate.initialize(tweet.getTweetTextURLS());
        Hibernate.initialize(tweet.getTweetRetweeters());
        Hibernate.initialize(tweet.getTweetRetweets());
    }

    public List<Tweet> saveTwitterUserTweets(TwitterUser twitterUser) {
        if (twitterUser.getUserTweets() == null) {
            twitterUser.setUserTweets(new ArrayList<>());
        }
        return tweetRepository.saveAll(twitterUser.getUserTweets());
    }

    public List<Tweet> getTweets() {
        return new ArrayList<>(tweetRepository.findAll());
    }
}
