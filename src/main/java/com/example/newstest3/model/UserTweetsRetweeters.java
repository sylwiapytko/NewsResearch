package com.example.newstest3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@NoArgsConstructor
public class UserTweetsRetweeters {

    @Id
    @GeneratedValue
    String thisnewid;

    Long userId;
    Long tweetId;
    Long retweetersId;

    public UserTweetsRetweeters(Long userId,
            Long tweetId,
            Long retweetersId) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.retweetersId= retweetersId;
    }
}
