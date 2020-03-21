package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
@Log
@Service
public class TwitterTweetService {

    @Autowired
    Twitter twitter;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    SleepService sleepService;


    public List<Tweet> fetchUserTweets(TwitterUser twitterUser) {
        List<Status> statuses = retriveUserStatuses(twitterUser);

        return retriveTweetsfromStatuses(statuses, twitterUser);
    }

    private List<Status> retriveUserStatuses(TwitterUser twitterUser) {
        List<Status> statuses = new ArrayList<>();
        int pageno = 1;
        while(true) {
            int size = statuses.size(); // actual tweets count we got

            statuses.addAll(retriveUserStatusesPage(pageno, twitterUser));

            System.out.println("total got : " + statuses.size());
            if (statuses.size() == size) { break; } // we did not get new tweets so we have done the job

            pageno++;
            sleepService.sleepForTime(1000);

        }
        return statuses;
    }


    private List<Status> retriveUserStatusesPage(int pageno, TwitterUser twitterUser) {
        Paging page = new Paging(pageno, 200);
        try {
            return twitter.getUserTimeline(twitterUser.getScreenName(), page);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Tweet> retriveTweetsfromStatuses(List<Status> statusesNew, TwitterUser twitterUser) {
        List<Tweet> tweets = new ArrayList<>();
        for(Status status: statusesNew){
            tweets.add(retriveTweetfromStatus(status, twitterUser));
        }
        twitterUser.addTweets(tweets);
        return tweets;
    }

    private Tweet retriveTweetfromStatus(Status status, TwitterUser twitterUser) {
        Tweet tweet = new Tweet();
        BeanUtils.copyProperties(status,tweet);
        tweet.setTwitterUserScreenName(twitterUser.getScreenName());
        tweet.setTextLength();
        return tweet;
    }
}
