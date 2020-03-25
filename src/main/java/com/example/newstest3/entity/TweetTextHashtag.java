package com.example.newstest3.entity;

import lombok.*;
import twitter4j.URLEntity;

import javax.persistence.*;

@Entity
@Table(name="TWITTER_TWEET_TEXT_HASHTAG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetTextHashtag {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TWEET_ID")
    private Tweet tweet;

    private String hashtag;




}
