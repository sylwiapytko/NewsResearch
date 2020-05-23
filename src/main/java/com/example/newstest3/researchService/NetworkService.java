package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TweetTextURL;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.model.UserUserConnection;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.TweetTextURLRepository;
import com.example.newstest3.repository.UserRepository;
import com.example.newstest3.repository.UserTweetRetweeterRepository;
import com.example.newstest3.service.TweetService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.User;

import java.io.*;
import java.util.*;

@Service
public class NetworkService {
    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTweetRetweeterRepository userTweetRetweeterRepository;
    @Autowired
    TweetTextURLRepository tweetTextURLRepository;

    @Value("${date.retweeters.start}")
    private String retweetersDateStart;

    @Value("${date.retweeters.end}")
    private String retweetersDateEnd;


@Transactional
    public void fetchGraphEdges() {
        String dateStartString = "22/03/2020 00:00";
        String dateEndTotalString = "11/04/2020 00:00";

        Date dateStart = utils.formatStringtoDate(dateStartString);
        Date dateEndTotal = utils.formatStringtoDate(dateEndTotalString);


        List<TwitterUser> twitterUserList =userRepository.findAllByAccountClassificationEquals(AccountClassification.JUNK);
        twitterUserList.addAll(userRepository.findAllByAccountClassificationEquals(AccountClassification.MAINSTREAM));
        twitterUserList.addAll(userRepository.findAllByAccountClassificationEquals(AccountClassification.BIGMAINSTREAM));
        System.out.println("num of users : "+ twitterUserList.size());

        userUserConnections(twitterUserList, dateStart, dateEndTotal);

        userLinkConnection(twitterUserList, dateStart, dateEndTotal);

    }



    private void userUserConnections(List<TwitterUser> twitterUserList, Date dateStart, Date dateEndTotal) {
        List<UserUserConnection> userUserConnectionListAll= new ArrayList<>();
        for(TwitterUser twitterUser : twitterUserList){

            List<Tweet> tweetList = tweetRepository.findNotOgiginalTweets( twitterUser.getId(), dateStart, dateEndTotal);
            // tweetList.forEach(this::initializeTwitterUsersData);
            System.out.println(twitterUser.getScreenName() + " : " + tweetList.size());

            Map<String, Integer> userConnectionsMap = new HashMap<>();
            for (Tweet tweet: tweetList){

                User connectedUser = tweet.getRetweetedStatus().getUser();

                //if(tweetList.stream().anyMatch(connectedUser::equals)){
                userConnectionsMap.merge(connectedUser.getScreenName(), 1, Integer::sum);
                // }
            }
            for(String conectedUser: userConnectionsMap.keySet()){
                if(userConnectionsMap.get(conectedUser)>=10){
                    userUserConnectionListAll.add(new UserUserConnection(twitterUser.getScreenName(), conectedUser, userConnectionsMap.get(conectedUser)));
                }
            }
        }

        writeToCSV(userUserConnectionListAll);

    }

    private void userLinkConnection(List<TwitterUser> twitterUserList, Date dateStart, Date dateEndTotal) {

        List<UserUserConnection> userUserConnectionListAll = new ArrayList<>();
        for (TwitterUser twitterUser : twitterUserList) {

            List<Long> tweetList = tweetRepository.findOgiginalTweetswithURLs(twitterUser.getId(), dateStart, dateEndTotal);
            List<String> tweetTextURLList = tweetTextURLRepository.findUrlsByTweetIdInList(tweetList);
            // tweetList.forEach(this::initializeTwitterUsersData);
            System.out.println(twitterUser.getScreenName() + " : " + tweetList.size());

            Map<String, Integer> userConnectionsMap = new HashMap<>();
            for (Long tweet : tweetList) {

            }
        }
    }

    private static final String CSV_SEPARATOR = " ";
    private static void writeToCSV(List<UserUserConnection> userUserConnectionListAll)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("userUserConnectionList.csv"), "UTF-8"));
            for (UserUserConnection userUserConnection : userUserConnectionListAll)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(userUserConnection.getUserScreenName());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(userUserConnection.getRetweetedUserScreenName());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(userUserConnection.getCount());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){
            System.out.println("Opened File!");
        }
    }

    private void createGraph(Tweet tweet) {
    }

}
