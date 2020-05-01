package com.example.newstest3.repository;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findAllByTwitterUserEquals(TwitterUser twitterUserId);


    @Query("SELECT t FROM Tweet t  " +
            "JOIN TwitterUser u " +
            "on u.id = t.twitterUser.id " +
            "where u.accountClassification = :accountClassification " +
            "and t.createdAt > :startDate  " +
            "and t.createdAt < :endDate ")
    List<Tweet> findTweetsByAccountClassification(
            @Param("accountClassification") AccountClassification accountClassification,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Tweet t  " +
            "JOIN TwitterUser u " +
            "on u.id = t.twitterUser.id " +
            "where u.accountClassification = :accountClassification " +
            "and t.createdAt > :startDate  " +
            "and t.createdAt < :endDate " +
            "and t.retweetCount > 0 ")
    List<Tweet> findTweetsByAccountClassificationAndTimeParams(
            @Param("accountClassification") AccountClassification accountClassification,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Tweet t  " +
            "JOIN TwitterUser u " +
            "on u.id = t.twitterUser.id " +
            "where u.accountClassification = :accountClassification " +
            "and t.createdAt > :startDate  " +
            "and t.createdAt < :endDate " +
            "and t.retweetCount > 0 " +
            "and t.retweetedFetchedRetweetersCount=0")
    List<Tweet> findTweetsByAccountClassificationAndTimeParamsAndZeroRetweetersFetched(
            @Param("accountClassification") AccountClassification accountClassification,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);





}
