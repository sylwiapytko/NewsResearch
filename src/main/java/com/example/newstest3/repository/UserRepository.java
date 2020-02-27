package com.example.newstest3.repository;

import com.example.newstest3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {


}
