package com.example.newstest3.repository;

import com.example.newstest3.entity.TwitterUserMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterUserMetricsRepository extends JpaRepository<TwitterUserMetrics, Integer> {
}
