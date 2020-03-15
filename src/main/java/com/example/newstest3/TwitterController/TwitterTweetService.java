package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.TweetRepository;
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

@Service
public class TwitterTweetService {

    @Autowired
    Twitter twitter;
    @Autowired
    private TweetRepository tweetRepository;


    public void fetchUserTweets(TwitterUser twitterUser) {
        List<Status> statuses = new ArrayList<>();
        int pageno = 1;
        while(true) {
            System.out.println("getting tweets");
            int size = statuses.size(); // actual tweets count we got
            Paging page = new Paging(pageno, 200);

            try {
                statuses.addAll(twitter.getUserTimeline(twitterUser.getName(), page));

                for(Status status: statuses){
                    Tweet tweet = new Tweet();
                    BeanUtils.copyProperties(status,tweet);
                    tweet.setTwitterUserScreenName(twitterUser.getName());
                    tweet.setTextLength();
                    twitterUser.addTweet(tweet);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            System.out.println("total got : " + statuses.size());
            if (statuses.size() == size) { break; } // we did not get new tweets so we have done the job
            pageno++;

            try {
                sleep(1000); // 900 rqt / 15 mn <=> 1 rqt/s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
