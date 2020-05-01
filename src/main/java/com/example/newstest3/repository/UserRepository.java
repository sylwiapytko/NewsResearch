package com.example.newstest3.repository;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<TwitterUser, Integer> {


    TwitterUser findFirstByScreenName(String screenName);
    List<TwitterUser> findAllByAccountClassificationEquals(AccountClassification accountClassification);

    @Query("SELECT u.id  FROM TwitterUser  u "+
            "where u.accountClassification = :accountClassification ")
    List<Long> findIdsByAccountClassificationEquals( @Param("accountClassification") AccountClassification accountClassification);

}
