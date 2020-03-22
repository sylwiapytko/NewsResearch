package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.FollowerRepository;
import com.example.newstest3.repository.UserRepository;
import com.example.newstest3.service.FollowerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TwitterUserService {

    @Autowired
    Twitter twitter;

    @Autowired
    FollowerService followerService;

    public TwitterUser fetchTwitterUser(String userName)  {
        User user = null;
        TwitterUser twitterUser = new TwitterUser();
        try {
            user = twitter.showUser(userName);
            BeanUtils.copyProperties(user, twitterUser);
            twitterUser.setURLExpanded();
            twitterUser.setURLTwitter();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        //TODO: if user doesnt exist then app is saving empty row with id =0
        return twitterUser;
    }

        public List<Long> getFollowersIDList ( TwitterUser twitterUser) throws TwitterException {
        long cursor =-1L;
        IDs ids;
        List<Long> followersIDList = new ArrayList<>();
        do {
            ids = twitter.getFollowersIDs(twitterUser.getId(), cursor);
            //Arrays.asList(ids.getIDs()).stream().map(followerService::createFollowerbyId)
            //twitterUser.addFollowers();
            for(long userID : ids.getIDs()){
                followersIDList.add(userID);
            }
        } while((cursor = ids.getNextCursor())!=0 );
        return  followersIDList;
    }
}
