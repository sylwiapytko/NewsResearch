package com.example.newstest3.repository;

import com.example.newstest3.entity.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<TwitterUser, Integer> {


    TwitterUser findFirstByScreenName(String screenName);
}
