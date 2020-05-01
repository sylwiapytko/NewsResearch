package com.example.newstest3.repository;

import com.example.newstest3.entity.Retweeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetweeterRepository extends JpaRepository<Retweeter, Integer> {

    @Query("SELECT r.retweeterId  FROM Retweeter  r "+
            "where r.tweet.id = :tweetId ")
    List<Long> findRetweetersByTweetId(@Param("tweetId") Long tweetId);
}
