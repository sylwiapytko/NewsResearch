package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="TWITTER_USER_METRICS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwitterUserMetrics {
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private long id;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_NAME")
    private TwitterUser twitterUser;

    private int statusesCount;
    

    private int statusesFetchedCount;
    private int followersCount;
    private int friendsCount; //followingsCount
    private int favouritesCount;
    private int listedCount;

    public void setStatusesFetchedCount() {
        this.statusesFetchedCount = twitterUser.getUserTweets().size();
    }

}
