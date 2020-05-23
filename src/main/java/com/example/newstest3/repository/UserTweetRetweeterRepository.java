package com.example.newstest3.repository;

import com.example.newstest3.model.UserTweetsRetweeters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserTweetRetweeterRepository extends JpaRepository<UserTweetsRetweeters, Integer> {
    @Query(
            "SELECT new com.example.newstest3.model.UserTweetsRetweeters( u.id, t.id, r.id)   FROM Retweeter  r " +
                    "join Tweet t " +
                    "on t.id = r.tweet.id " +
                    "join TwitterUser u " +
                    "on u.id = t.twitterUser.id "+
                    "where u.id = :userID " +
                    "and t.createdAt > :startDate  " +
                    "and t.createdAt < :endDate " +
                    "and t.retweet = false ")
    List<UserTweetsRetweeters> findRetweetersByUserId(Long userID, Date startDate, Date endDate);
}
