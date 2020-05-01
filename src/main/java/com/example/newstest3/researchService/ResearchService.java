package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResearchService {

    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;


}
