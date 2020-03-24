package com.example.newstest3.repository;

import com.example.newstest3.entity.Retweeter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweeterRepository extends JpaRepository<Retweeter, Integer> {
}
