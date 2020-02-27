package com.example.newstest3.dao;

import com.example.newstest3.TwitterController.TwitterService;
import com.example.newstest3.entity.User;
import com.example.newstest3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
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

//    private static final String PERSISTENCE_UNIT_NAME = "User";
//    private static EntityManagerFactory factory =
//            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public void fetchTwitterUsers(){
 //       usersNames.stream().map(s -> twitterService.printUser(s)).forEach(user -> userRepository.save(user));
        usersNames.stream().map(twitterService::printUser).forEach(userRepository::save);
    }


    public void createUser() {
//        EntityManager entityManager = factory.createEntityManager();
        System.out.println("hejj im here");
        User newUser = new User(111L, "Adam", "adamname", "this is description");
        userRepository.save(newUser);
        newUser = new User(112L, "qAdam", "adamname", "this is description");
        userRepository.save(newUser);
        newUser = new User(113L, "wAdam", "adamname", "this is description");
        userRepository.save(newUser);
    }

    public List<User> getUsers() {

        //List<User> users = new ArrayList<>();
        //userRepository.findAll().forEach(user -> users.add(user));
        return new ArrayList<>(userRepository. findAll());
    }
}
