package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.TweetTextHashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterTweetTextHashtagService {

    @Autowired
    Twitter twitter;
    @Autowired
    SleepService sleepService;

    public List<TweetTextHashtag> fetchHashtagsfromTweet(Status status){
        List<TweetTextHashtag> tweetTextHashtags= new ArrayList<>();
        HashtagEntity[] hashtagEntities = status.getHashtagEntities();
        for(HashtagEntity hashtagEntity : hashtagEntities) {
            TweetTextHashtag tweetTextHashtag = new TweetTextHashtag();
            tweetTextHashtag.setHashtag(hashtagEntity.getText());
            tweetTextHashtags.add(tweetTextHashtag);
        }
        return tweetTextHashtags;
    }
}
