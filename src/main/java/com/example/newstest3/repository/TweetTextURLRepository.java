package com.example.newstest3.repository;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TweetTextURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetTextURLRepository extends JpaRepository<TweetTextURL, Integer> {
}
