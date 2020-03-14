package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitter4j.URLEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="TWITTER_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterUser {
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private long id;

    @Column(name= "USER_NAME")
    private String name;
    private String screenName;


    private String URLTwitter;
    private String URL;
    private String URLExpanded;
    private URLEntity URLEntity;

    private Date createdAt;
    private String location;
    private String lang;
    private String description;
    private boolean contributorsEnabled;

    private boolean defaultProfile;
    private boolean defaultProfileImage;
    private boolean profileUseBackgroundImage;
    private boolean verified;

    private int statusesCount;
    private int followersCount;
    private int friendsCount; //followingsCount
    private int favouritesCount;
    private int listedCount;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tweet> tweets;


    public void setURLTwitter() {
        this.URLTwitter = "https://twitter.com/" + this.getScreenName();
    }
    public void setURLExpanded() {
        this.URLExpanded = this.URLEntity.getExpandedURL();
    }

    public void addTweet(Tweet tweet) {
        if (this.tweets == null) {
            this.tweets = new ArrayList<>();
        }
        // bi-directional reference
        this.tweets.add(tweet);
        tweet.setTwitterUser(this);
    }
    public void addTweets(List<Tweet> tweets) {
        if (this.tweets == null) {
            this.tweets = new ArrayList<>();
        }
        // bi-directional reference
        this.tweets.addAll(tweets);
        tweets.forEach(tweet ->tweet.setTwitterUser(this));
    }
}
