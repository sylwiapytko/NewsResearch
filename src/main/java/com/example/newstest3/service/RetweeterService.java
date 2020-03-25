package com.example.newstest3.service;

import com.example.newstest3.entity.Retweeter;
import org.springframework.stereotype.Service;

@Service
public class RetweeterService {

    public Retweeter createRetweeterbyId(long retweeterId){
        return new Retweeter().builder().retweeterId(retweeterId).build();
    }
}
