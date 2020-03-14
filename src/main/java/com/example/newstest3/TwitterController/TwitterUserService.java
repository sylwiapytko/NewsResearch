package com.example.newstest3.TwitterController;

import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@Service
public class TwitterUserService {

    @Autowired
    Twitter twitter;

    public TwitterUser fetchTwitterUser(String userName)  {
        User user = null;
        TwitterUser twitterUser = new TwitterUser();
        try {
            user = twitter.showUser(userName);
            BeanUtils.copyProperties(user, twitterUser);
            twitterUser.setURLExpanded();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        //TODO: if user doesnt exist then app is saving empty row with id =0
        return twitterUser;
    }
}
