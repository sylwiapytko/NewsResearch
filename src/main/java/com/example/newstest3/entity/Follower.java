package com.example.newstest3.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TWITTER_ANONYMOUS_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follower {

    @Id
    @Column(name = "FOLLOWER_ID", nullable = false)
    private long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private TwitterUser twitterUser;
}
