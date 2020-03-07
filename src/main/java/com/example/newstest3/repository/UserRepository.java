package com.example.newstest3.repository;

import com.example.newstest3.entity.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TwitterUser, Integer> {


}
