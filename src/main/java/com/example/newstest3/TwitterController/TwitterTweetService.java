package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log
@Service
public class TwitterTweetService {

    @Value("${date.lastFetch}")
    private String lastFetchDate;

    @Autowired
    Twitter twitter;

    @Autowired
    SleepService sleepService;

    @Autowired
    StatusToTweet statusToTweet;


    public List<Tweet> fetchUserTweets(TwitterUser twitterUser) {
        List<Status> statuses = retriveUserStatuses(twitterUser);
        twitterUser.addTweets(statusToTweet.retriveTweetsfromStatuses(statuses));
        return twitterUser.getUserTweets();
    }

    private List<Status> retriveUserStatuses(TwitterUser twitterUser) {
        List<Status> statuses = new ArrayList<>();
        int pageno = 1;
        while(true) {

            List<Status> statusesNew = retriveUserStatusesPage(pageno, twitterUser);
            if(statusesNew.isEmpty()){ break;}
            else{
                for(Status status: statusesNew){
                    if(checkStatusAgeIfNew(status.getCreatedAt())) {
                        statuses.add(status);
                    }
                    else{
                        System.out.println("Found last fetch date : " +status.getCreatedAt() +" tweets: " + statuses.size());
                        return statuses;
                    }
                }
                pageno++;
                //sleepService.sleepForTime(1000);
            }
        }
        System.out.println("tweets : " + statuses.size());
        return statuses;
    }


    private List<Status> retriveUserStatusesPage(int pageno, TwitterUser twitterUser) {
        Paging page = new Paging(pageno, 200);
        try {
            return twitter.getUserTimeline(twitterUser.getScreenName(), page);
        } catch (TwitterException e) {
            sleepService.printErrorAndSleepSec(e, 60 * 5);
            return null;
        }
    }
    private boolean checkStatusAgeIfNew(Date createdAt) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = formatter.parse(lastFetchDate);
            if(createdAt.compareTo(date)<0){
                return false;
            }
            else{
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}
