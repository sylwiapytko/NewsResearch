package com.example.newstest3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUserConnection {

    String userScreenName;
    String retweetedUserScreenName;
    long count;
}
