package com.example.newstest3.entity;

import lombok.*;
import twitter4j.URLEntity;

import javax.persistence.*;

@Entity
@Table(name="TWITTER_TWEET_TEXT_URL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetTextURL {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TWEET_ID")
    private Tweet tweet;

    private URLEntity urlEntity;
    private String URL;

    @Column(length  = 750)
    private String URLExpanded;

    public void setURL() {
        this.URL = this.urlEntity.getURL();
    }

    public void setURLExpanded() {
        this.URLExpanded = this.urlEntity.getExpandedURL();
    }



}
