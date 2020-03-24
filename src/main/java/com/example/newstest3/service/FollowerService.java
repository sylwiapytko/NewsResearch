package com.example.newstest3.service;

import com.example.newstest3.entity.Follower;
import com.example.newstest3.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {
    @Autowired
    private FollowerRepository followerRepository;

    public Follower createFollowerbyId(long followerId){
        return new Follower().builder().followerId(followerId).build();
    }
}
