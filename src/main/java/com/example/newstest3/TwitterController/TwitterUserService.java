package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.Follower;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.service.FollowerService;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class TwitterUserService {

    @Autowired
    Twitter twitter;

    @Autowired
    FollowerService followerService;

    @Autowired
    SleepService sleepService;

    public TwitterUser fetchTwitterUser(String userName) {
        User user = null;
        TwitterUser twitterUser = new TwitterUser();
        try {
            user = twitter.showUser(userName);
            BeanUtils.copyProperties(user, twitterUser);
            twitterUser.setURLExpanded();
            twitterUser.setURLTwitter();
        } catch (TwitterException e) {
            System.out.println("User not found - " + userName);
            return  null;
            //sleepService.printErrorAndSleepSec(e, 60);
        }

        //TODO: if user doesnt exist then app is saving empty row with id =0
        return twitterUser;
    }

    public List<Follower> fetchUserFollowers(TwitterUser twitterUser) {//limit 900 na 15 min.

        twitterUser.addUserFollowers(retriveFollowersIDs(twitterUser));

        return twitterUser.getUserFollowers();
    }

    private List<Follower> retriveFollowersIDs(TwitterUser twitterUser) {
        long cursor = -1L;
        IDs ids = null;
        List<Follower> userFollowers = new ArrayList<>();

            do {
                ids = retriveFollowersIDsbyCoursor( twitterUser, cursor);
                userFollowers.addAll(retriveFollowersfromIDs(ids));
                System.out.println("followers " + userFollowers.size());

            } while ((cursor = ids.getNextCursor()) != 0 && userFollowers.size() < 500000);

        return userFollowers;
    }

    private IDs retriveFollowersIDsbyCoursor(TwitterUser twitterUser, long cursor) {
        try {
            sleepService.sleepForTime(65);
            return twitter.getFollowersIDs(twitterUser.getId(), cursor);
        } catch (TwitterException e) {
            sleepService.printErrorAndSleepSec(e, 60 * 5);
            return null;
        }
    }

    private List<Follower> retriveFollowersfromIDs(IDs ids) {
        return Arrays.stream(ids.getIDs())
                .boxed()
                .map(followerService::createFollowerbyId)
                .collect(Collectors.toList());
    }
}
