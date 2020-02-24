package com.example.newstest3.controller;

import com.example.newstest3.dao.UserDAO;
import com.example.newstest3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @PostMapping("/createDummyUsers")
    public void createDummyUsers(){
        userDAO.createUser();
    }


    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userDAO.getUsers();
    }


}
