package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="TWITTER_TWEET")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    @Column(name = "TWEET_ID", nullable = false, unique = true)
    private long id;

    private String twitterUserScreenName;

    @Column(name = "TWEET_TEXT", length  = 300)
    private String text;

    @Column(name = "TWEET_TEXT_LENGTH")
    private int textLength;

    private Date createdAt;
    private String source;
    private GeoLocation geoLocation;
    private Place place;
    private String Lang;

    private long replyToStatusId;
    private long replyToUserId;
    private Status retweetedStatus;

    private long quotedStatusId;
    private Status quotedStatus;

    private boolean favorited;
    private boolean retweeted;
    private boolean retweet;

    private int favoriteCount;
    private int retweetCount;


    public void setTwitterUserScreenName(String twitterUserScreenName) {
        this.twitterUserScreenName = twitterUserScreenName;
    }

    public void setTextLength() {
        this.textLength = this.text.length();
    }
}
