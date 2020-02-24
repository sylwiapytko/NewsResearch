package com.example.newstest3.dao;

import com.example.newstest3.entity.User;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createUser() {
        System.out.println("hejj im here");
        User newUser = new User(111L, "Adam", "adamname", "this is description");
        entityManager.persist(newUser);
        newUser = new User(112L, "qAdam", "adamname", "this is description");
        entityManager.persist(newUser);
        newUser = new User(113L, "wAdam", "adamname", "this is description");
        entityManager.persist(newUser);
    }

    @Override
    @Transactional
    public List<User> getUsers() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }
}
