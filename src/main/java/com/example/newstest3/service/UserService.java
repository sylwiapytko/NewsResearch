package com.example.newstest3.service;


import com.example.newstest3.TwitterController.TwitterUserService;
import com.example.newstest3.entity.Follower;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  {

    @Value("#{'${usersnames}'.split(',')}")
    private List<String> usersNames;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TwitterService twitterService;
    
    @Autowired
    TweetService tweetService;

    @Autowired
    TwitterUserService twitterUserService;



    public List<TwitterUser> fetchTwitterUsersInfo(){
        List<TwitterUser>  twitterUserList= new ArrayList<>();;
        // usersNames.stream().map(s -> twitterService.printUser(s)).forEach(user -> userRepository.save(user));
        usersNames.stream().map(twitterUserService::fetchTwitterUser).forEach(twitterUserList::add);
        return twitterUserList;
    }

    public void fetchTwitterUsersData(TwitterUser twitterUser) {
        fetchTwitterUserFollowers(twitterUser);
        tweetService.fetchTwitterUserTweets(twitterUser);
    }

    private List<Follower> fetchTwitterUserFollowers(TwitterUser twitterUser) {
        return  twitterUserService.fetchUserFollowers(twitterUser);
    }

    public TwitterUser saveTwitterUserInfo(TwitterUser twitterUser){
        return userRepository.save(twitterUser);
    }

    public List<TwitterUser> saveTwitterUsersInfo(List<TwitterUser> twitterUsers){
         return userRepository.saveAll(twitterUsers);
    }





    public List<TwitterUser> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
