package com.example.newstest3.repository;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findAllByTwitterUserEquals(TwitterUser twitterUserId);
}
