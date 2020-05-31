package com.example.newstest3.repository;

import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.entity.TweetTextURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetTextURLRepository extends JpaRepository<TweetTextURL, Integer> {


    @Query("SELECT u.URLExpanded  FROM  TweetTextURL u "+
            "where u.tweet.id in :tweetIdList ")
    List<String> findUrlsByTweetIdInList(@Param("tweetIdList") List<Long> tweetIdList);
}
