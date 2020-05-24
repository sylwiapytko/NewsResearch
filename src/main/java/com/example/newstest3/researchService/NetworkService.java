package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.entity.TwitterUser;
import com.example.newstest3.model.UserUserConnection;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.repository.TweetTextURLRepository;
import com.example.newstest3.repository.UserRepository;
import com.example.newstest3.repository.UserTweetRetweeterRepository;
import com.example.newstest3.service.TweetService;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.User;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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


        List<TwitterUser> twitterUserList = userRepository.findAllByAccountClassificationEquals(AccountClassification.JUNK);
        twitterUserList.addAll(userRepository.findAllByAccountClassificationEquals(AccountClassification.MAINSTREAM));
        twitterUserList.addAll(userRepository.findAllByAccountClassificationEquals(AccountClassification.BIGMAINSTREAM));
        twitterUserList.addAll(userRepository.findAllByAccountClassificationEquals(AccountClassification.FACTCHECK));
        System.out.println("num of users : " + twitterUserList.size());

        userUserConnections(twitterUserList, dateStart, dateEndTotal);

       // userLinkConnection(twitterUserList, dateStart, dateEndTotal);

    }


    private void userUserConnections(List<TwitterUser> twitterUserList, Date dateStart, Date dateEndTotal) {
        List<UserUserConnection> userUserConnectionListAll = new ArrayList<>();
        List<UserUserConnection> userUserConnectionListInside = new ArrayList<>();
        List<UserUserConnection> userUserConnectionListInsideandBig = new ArrayList<>();
        for (TwitterUser twitterUser : twitterUserList) {

            List<Tweet> tweetList = tweetRepository.findNotOgiginalTweets(twitterUser.getId(), dateStart, dateEndTotal);
            Map<String, Integer> userConnectionsMap = findConnectionsInTweets(tweetList);

            for (String conectedUser : userConnectionsMap.keySet()) {
                getUsersConnectionListAll(userUserConnectionListAll, twitterUser, userConnectionsMap, conectedUser, 1);
                getUsersConnectionListInside(twitterUserList, userUserConnectionListInside, twitterUser, userConnectionsMap, conectedUser);
                getUsersConnectionListInsideAndBigAndNoLoops(twitterUserList, userUserConnectionListInsideandBig, twitterUser, userConnectionsMap, conectedUser, 30);

            }
        }

        writeToCSV(userUserConnectionListAll, "userUserConnectionListAll");
        writeToCSV(userUserConnectionListInside, "userUserConnectionListInside");
        writeToCSV(userUserConnectionListInsideandBig, "userUserConnectionListInsideandBigAndExisting");

    }

    private Map<String, Integer> findConnectionsInTweets(List<Tweet> tweetList) {
        Map<String, Integer> connectionsMap = new HashMap<>();
        for (Tweet tweet : tweetList) {

            User connectedUser = tweet.getRetweetedStatus().getUser();
            connectionsMap.merge(connectedUser.getScreenName(), 1, Integer::sum);
        }
        return connectionsMap;
    }

    private void getUsersConnectionListAll(List<UserUserConnection> userUserConnectionListAll, TwitterUser twitterUser, Map<String, Integer> userConnectionsMap, String conectedUser, Integer minOccurences) {
        if (userConnectionsMap.get(conectedUser) >= minOccurences) {
            userUserConnectionListAll.add(new UserUserConnection(twitterUser.getScreenName(), conectedUser, userConnectionsMap.get(conectedUser)));
        }
    }

    private void getUsersConnectionListInside(List<TwitterUser> twitterUserList, List<UserUserConnection> userUserConnectionListInside, TwitterUser twitterUser, Map<String, Integer> userConnectionsMap, String conectedUser) {
        if(twitterUserList.stream().map(TwitterUser::getScreenName).anyMatch(conectedUser::equals)){
            userUserConnectionListInside.add(new UserUserConnection(twitterUser.getScreenName(), conectedUser, userConnectionsMap.get(conectedUser)));
        }
    }
    private void getUsersConnectionListInsideAndBigAndNoLoops(List<TwitterUser> twitterUserList, List<UserUserConnection> userUserConnectionListInside, TwitterUser twitterUser, Map<String, Integer> userConnectionsMap, String conectedUser, Integer minOccurences) {
        if((twitterUserList.stream().map(TwitterUser::getScreenName).anyMatch(conectedUser::equals)
                || userConnectionsMap.get(conectedUser) >= minOccurences)
                && !twitterUser.getScreenName().equals(conectedUser)){
            userUserConnectionListInside.add(new UserUserConnection(twitterUser.getScreenName(), conectedUser, userConnectionsMap.get(conectedUser)));
        }
        if((userUserConnectionListInside.stream().map(UserUserConnection::getRetweetedUserScreenName).anyMatch(conectedUser::equals)
                && userConnectionsMap.get(conectedUser) < minOccurences)
                && !twitterUser.getScreenName().equals(conectedUser)){
            userUserConnectionListInside.add(new UserUserConnection(twitterUser.getScreenName(), conectedUser, userConnectionsMap.get(conectedUser)));
        }
    }

    private void userLinkConnection(List<TwitterUser> twitterUserList, Date dateStart, Date dateEndTotal) {

        List<UserUserConnection> userUserConnectionListAll = new ArrayList<>();
        for (TwitterUser twitterUser : twitterUserList) {

            List<Long> tweetList = tweetRepository.findOgiginalTweetswithURLs(twitterUser.getId(), dateStart, dateEndTotal);
            List<String> tweetTextURLList = tweetTextURLRepository.findUrlsByTweetIdInList(tweetList);
            // tweetList.forEach(this::initializeTwitterUsersData);
            System.out.println(twitterUser.getScreenName() + " : " + tweetList.size() + " : " + tweetTextURLList.size());

            Map<String, Integer> userConnectionsMap = new HashMap<>();
            for (String tweetTextURL : tweetTextURLList) {
                try {
                    java.net.URL aURL = new URL(tweetTextURL);
                    if (tweetTextURL.length() <= 22 && !aURL.toString().equals("http://m.in")) {
                        aURL = new URL(expandSingleLevel(aURL.toString()));
                    }
                    userConnectionsMap.merge(aURL.getHost(), 1, Integer::sum);
                } catch (MalformedURLException e) {
                    System.out.println("url went wrong");
                }
            }
            for (String connectedDomain : userConnectionsMap.keySet()) {
                getUsersConnectionListAll(userUserConnectionListAll, twitterUser, userConnectionsMap, connectedDomain, 30);
            }


        }
        writeToCSV(userUserConnectionListAll, "linksNetwork");
    }

    public String expandSingleLevel(String url) {
        CloseableHttpClient client =
                HttpClientBuilder.create().disableRedirectHandling().build();

        HttpHead request = null;
        try {
            request = new HttpHead(url);
            HttpResponse httpResponse = client.execute(request);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 301 && statusCode != 302) {
                return url;
            }
            Header[] headers = httpResponse.getHeaders(HttpHeaders.LOCATION);
            //Preconditions.checkState(headers.length == 1);
            String newUrl = headers[0].getValue();
            return newUrl;
        } catch (IllegalArgumentException | IOException uriEx) {
            System.out.println("went bad... " + url + " ..................................................");
            return url;
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

    URLConnection connectURL(String strURL) {
        URLConnection conn = null;
        try {
            URL inputURL = new URL(strURL);
            conn = inputURL.openConnection();
            int test = 0;

        } catch (MalformedURLException e) {
            System.out.println("Please input a valid URL");
        } catch (IOException ioe) {
            System.out.println("Can not connect to the URL");
        }
        return conn;
    }

    private static final String CSV_SEPARATOR = " ";

    private static void writeToCSV(List<UserUserConnection> userUserConnectionListAll, String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".csv"), "UTF-8"));
            for (UserUserConnection userUserConnection : userUserConnectionListAll) {
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
        } catch (UnsupportedEncodingException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Opened File!");
        }
    }

    private void createGraph(Tweet tweet) {
    }

}
