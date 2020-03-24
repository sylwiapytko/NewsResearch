package com.example.newstest3.service;

import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.repository.RetweeterRepository;
import com.example.newstest3.repository.RetweeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetweeterService {
    @Autowired
    private RetweeterRepository retweeterRepository;
    
    public Retweeter createRetweeterbyId(long retweeterId){
        return new Retweeter().builder().retweeterId(retweeterId).build();
    }
}
