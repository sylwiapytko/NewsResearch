package com.example.newstest3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TWITTER_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private long id;

    @Column(name= "USER_NAME", length = 50)
    private String name;

    private String screenName;

    private String description;
}
