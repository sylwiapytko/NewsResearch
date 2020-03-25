package com.example.newstest3.service;

import com.example.newstest3.entity.Follower;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {

    public Follower createFollowerbyId(long followerId){
        return new Follower().builder().followerId(followerId).build();
    }
}
