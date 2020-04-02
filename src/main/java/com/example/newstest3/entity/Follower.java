package com.example.newstest3.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TWITTER_USER_FOLLOWER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue
    private long id;

    @Column(name = "FOLLOWER_ID", nullable = false)
    private long followerId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private TwitterUser twitterUser;
}
