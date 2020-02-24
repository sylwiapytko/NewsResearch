package com.example.newstest3.dao;

import com.example.newstest3.entity.User;

import java.util.List;

public interface UserDAO {
    public void createUser();
    public List<User> getUsers();

}
