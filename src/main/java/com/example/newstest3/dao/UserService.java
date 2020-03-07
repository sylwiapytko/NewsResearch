package com.example.newstest3.dao;

import com.example.newstest3.TwitterController.TwitterService;
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


    public void fetchTwitterUsers(){
        // usersNames.stream().map(s -> twitterService.printUser(s)).forEach(user -> userRepository.save(user));
        usersNames.stream().map(twitterService::printUser).forEach(userRepository::save);
    }



    public List<TwitterUser> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
