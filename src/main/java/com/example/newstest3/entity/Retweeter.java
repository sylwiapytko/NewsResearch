package com.example.newstest3.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name="TWITTER_TWEET_RETWEETER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Retweeter {

    @Id
    @Column(name = "FOLLOWER_ID", nullable = false)
    private long retweeterId;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private TwitterUser twitterUser;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TWEET_ID")
    private Tweet tweet;

}