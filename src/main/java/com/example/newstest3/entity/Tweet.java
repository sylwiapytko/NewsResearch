package com.example.newstest3.entity;

import lombok.*;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private TwitterUser twitterUser;

    @Column(name = "TWEET_TEXT", length  = 350)
    private String text;

    @Column(name = "TWEET_TEXT_LENGTH")
    private int textLength;

    private Date createdAt;
    private String source;
    private GeoLocation geoLocation;
    private Place place;
    private String Lang;

    private long inReplyToStatusId;
    private long inReplyToUserId;
    private Status retweetedStatus;

    private long quotedStatusId;
    private Status quotedStatus;

    private boolean favorited;
    private boolean retweeted;
    private boolean retweet;

    private int favoriteCount;
    private int retweetCount;
    private int retweetedFetchedRetweetsCount;
    private int retweetedFetchedRetweetersCount;

    private int tweetTextHashtagsFetchedCount;
    private int tweetTextURLSFetchedCount;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<TweetTextHashtag> tweetTextHashtags;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<TweetTextURL> tweetTextURLS;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<Retweeter> tweetRetweeters;

    @ManyToOne
    private Tweet tweetParent;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tweetParent")
    private List<Tweet> tweetRetweets;


    public void addtweetTextHashtags(List<TweetTextHashtag> tweetTextHashtags) {
        if (this.tweetTextHashtags == null) {
            this.tweetTextHashtags = new ArrayList<>();
        }
        // bi-directional reference
        this.tweetTextHashtags.addAll(tweetTextHashtags);
        tweetTextHashtags.forEach(tweet ->tweet.setTweet(this));
        setTweetTextHashtagsFetchedCount();
    }

    public void setTweetTextHashtagsFetchedCount() {
        this.tweetTextHashtagsFetchedCount = this.getTweetTextHashtags().size();
    }

    public void addtweetTextURL(List<TweetTextURL> tweetTextURLs) {
        if (this.tweetTextURLS == null) {
            this.tweetTextURLS = new ArrayList<>();
        }
        // bi-directional reference
        this.tweetTextURLS.addAll(tweetTextURLs);
        tweetTextURLs.forEach(tweet ->tweet.setTweet(this));
        setTweetTextURLSFetchedCount();
    }

    public void setTweetTextURLSFetchedCount() {
        this.tweetTextURLSFetchedCount = this.getTweetTextURLS().size();
    }


    public void addtweetRetweeter(List<Retweeter> retweeter) {
        if (this.tweetRetweeters == null) {
            this.tweetRetweeters = new ArrayList<>();
        }
        // bi-directional reference
        this.tweetRetweeters.addAll(retweeter);
        tweetRetweeters.forEach(tweet ->tweet.setTweet(this));
        setRetweetedFetchedRetweetersCount();
    }
    public void setRetweetedFetchedRetweetersCount() {
        this.retweetedFetchedRetweetersCount = tweetRetweeters.size();
    }

    public void addtweetRetweets(List<Tweet> tweetRetweets) {
        if (this.tweetRetweets == null) {
            this.tweetRetweets = new ArrayList<>();
        }
        this.tweetRetweets.addAll(tweetRetweets);
        tweetRetweets.forEach(tweet ->tweet.setTweetParent(this));
        //also retweetedStatus points to parent tweet.
        setRetweetedFetchedRetweetsCount();
    }

    public void setRetweetedFetchedRetweetsCount() {
        this.retweetedFetchedRetweetsCount = this.tweetRetweets.size();
    }


    public void setTextLength() {
        this.textLength = this.text.length();
    }
}
