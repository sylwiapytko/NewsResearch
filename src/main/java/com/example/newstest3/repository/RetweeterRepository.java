package com.example.newstest3.repository;

import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.model.UserTweetsRetweeters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RetweeterRepository extends JpaRepository<Retweeter, Integer> {

    @Query("SELECT r.retweeterId  FROM Retweeter  r "+
            "where r.tweet.id = :tweetId ")
    List<Long> findRetweetersByTweetId(@Param("tweetId") Long tweetId);

    @Query("SELECT r  FROM Retweeter  r "+
            "where r.tweet.id in :tweetIdList ")
    List<Retweeter> findRetweetersByTweetIdInList(@Param("tweetIdList") List<Long> tweetIdList);


//    @Query(
//            "SELECT com.example.newstest3.model.UserTweetsRetweeters(t.id)  FROM Retweeter  r " +
//            "join Tweet t " +
//            "on t.id = r.tweet.id " +
//            "join TwitterUser u " +
//            "on u.id = t.twitterUser.id "+
//            "where u.id = :userID " +
//            "and t.createdAt > :startDate  " +
//            "and t.createdAt < :endDate " +
//            "and t.retweet = false ")
//    List<UserTweetsRetweeters> findRetweetersByUserId(Long userID, Date startDate, Date endDate);
}
