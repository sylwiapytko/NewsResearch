package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TweetTextURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterTweetTextURLService {

    @Autowired
    Twitter twitter;

    public void fetchURLfromTweet(Tweet tweet){
        try {
            Status status  = twitter.showStatus(tweet.getId());
            URLEntity[] urls = status.getURLEntities();
            for(URLEntity urlEntity : urls) {
                TweetTextURL tweetTextURL = new TweetTextURL();
                tweetTextURL.setUrlEntity(urlEntity);
                tweetTextURL.setURL();
                tweetTextURL.setURLExpanded();
                tweet.addtweetTextURL(tweetTextURL);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
