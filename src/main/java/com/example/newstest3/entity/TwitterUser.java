package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitter4j.URLEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    public void setURLExpanded() {
        this.URLExpanded = this.URLEntity.getExpandedURL();
    }

}
