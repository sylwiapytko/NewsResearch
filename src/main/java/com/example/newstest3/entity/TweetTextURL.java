package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TWEET_ID")
    private Tweet tweet;

    private URLEntity urlEntity;
    private String URL;
    private String URLExpanded;

    public void setURL() {
        this.URL = this.urlEntity.getURL();
    }

    public void setURLExpanded() {
        this.URLExpanded = this.urlEntity.getExpandedURL();
    }



}
