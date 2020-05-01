package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Retweeter;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.service.RetweeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterRetweeterService {
    
    @Autowired
    Twitter twitter;
    
    @Autowired
    SleepService sleepService;
    
    @Autowired
    RetweeterService retweeterService;

    @Autowired
    private TweetRepository tweetRepository;

    public List<Retweeter> fetchTweetRetweeters(Tweet tweet) {
        tweet.addtweetRetweeter(retriveTweetRetweeters(tweet));
        //tweetRepository.save(tweet);
        System.out.println("retweetersIDs for tweet: "+ tweet.getId() +" count " + tweet.getRetweetCount() +" retrieved " + tweet.getTweetRetweeters().size() );
        return  tweet.getTweetRetweeters();
    }
    public List<Retweeter> retriveTweetRetweeters(Tweet tweet) { //max 75 tweetow!
        long cursor =-1L;
        IDs ids = null;
        List<Retweeter> retweetersIDs = new ArrayList<>();
        do {
            ids = retriveFollowersIDsbyCoursor( tweet,   cursor);
            retweetersIDs.addAll(retriveFollowersfromIDs(ids));

        } while((cursor = ids.getNextCursor())!=0 );
                
        return  retweetersIDs;
    }

    private IDs retriveFollowersIDsbyCoursor(Tweet tweet, long cursor) {
        try {
            sleepService.sleepForTime(21);
            return twitter.getRetweeterIds(tweet.getId(), 100,  cursor);
        } catch (TwitterException e) {
            sleepService.printErrorAndSleepSec(e, 1);
            return  null;
        }
    }

    private List<Retweeter> retriveFollowersfromIDs(IDs ids) {
        if (ids.equals(null)) return null;
        return Arrays.stream(ids.getIDs())
                .boxed()
                .map(retweeterService::createRetweeterbyId)
                .collect(Collectors.toList());
    }
}
