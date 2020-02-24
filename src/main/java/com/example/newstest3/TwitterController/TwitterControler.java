package com.example.newstest3.TwitterController;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * Created by Sylwia on 08-Dec-19.
 */
public class TwitterControler {

    public Twitter getTwitterinstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("**")
                .setOAuthConsumerSecret("**")
                .setOAuthAccessToken("**")
                .setOAuthAccessTokenSecret("**");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return  twitter;
    }

    public String createTweet(Twitter twitter,String tweet) throws TwitterException {
        Status status = twitter.updateStatus(tweet);
        System.out.println("Print");
        return status.getText();
    }
    public List<String> getTimeLine(Twitter twitter) throws TwitterException {
        return twitter.getHomeTimeline().stream()
                .map(item -> item.getText())
                .collect(Collectors.toList());
    }

    public List<Status> printUserTimeline(Twitter twitter, String userName, Integer pagingPage, Integer pagingCount) throws TwitterException {
        Paging paging = new Paging(pagingPage, pagingCount);
        List<Status> statuses = twitter.getUserTimeline(userName,paging);
        for (Status status : statuses) {
            System.out.println(status.getId() + " "+ status.getText());
            System.out.println("favorite "+ status.getFavoriteCount());
            System.out.println("retweets "+status.getRetweetCount());
            System.out.println("geo "+status.getGeoLocation());
            System.out.println("place "+status.getPlace());
            System.out.println("scopes "+status.getScopes());
            System.out.println("source "+status.getSource());
            System.out.println("countries "+status.getWithheldInCountries());
            System.out.println("isRetweet "+status.isRetweet());
            System.out.println("created at  "+status.getCreatedAt());
            System.out.println("id  "+status.getId());
            System.out.println("lang  "+status.getLang());
            System.out.println("replyToStatus  "+status.getInReplyToStatusId());
            System.out.println("getQuotedStatusId  "+status.getQuotedStatusId());
            if(status.isRetweet()){
                System.out.println("getRetweetedStatus  "+status.getRetweetedStatus().getId());
            }
            System.out.println("userid  "+status.getUser().getId());
            URLEntity[] urls = status.getURLEntities();
            for(URLEntity urlEntity : urls) {
                System.out.println("Website : "+urlEntity.getExpandedURL());
               // System.out.println("Website : "+urlEntity.getURL());
            }
            String url= "https://twitter.com/" + status.getUser().getScreenName()
                    + "/status/" + status.getId();
            System.out.println(url);
            System.out.println();
        }
        return  statuses;
    }
    public List<Status> getUserTimelineList(Twitter twitter, String userName) throws TwitterException, InterruptedException {
        List<Status> statuses = new ArrayList<>();
        int pageno = 1;
        while(true) {
            System.out.println("getting tweets");
            int size = statuses.size(); // actual tweets count we got
            Paging page = new Paging(pageno, 200);
            statuses.addAll(twitter.getUserTimeline(userName, page));
            System.out.println("total got : " + statuses.size());
            if (statuses.size() == size) { break; } // we did not get new tweets so we have done the job
            pageno++;
            sleep(1000); // 900 rqt / 15 mn <=> 1 rqt/s
        }
        return statuses;
    }
    public void writeUserTimelineToFile(Twitter twitter, String userName)
            throws IOException, TwitterException, InterruptedException {
        String str = "Hello";
        BufferedWriter writer = new BufferedWriter(new FileWriter("testFile.txt"));
        List<Status> statuses = getUserTimelineList(twitter, userName);
        int i=1;
        for (Status status : statuses) {
            writer.write(i+ " "+status.getCreatedAt().toString() +" "+ status.getFavoriteCount()+ " " +status.getText() );
            writer.newLine();
            i++;
        }
      writer.close();
    }

    public void printURLfromMyTweets(Twitter twitter) throws TwitterException {
        List<Status> statusList = twitter.getHomeTimeline();
        for (Status status : statusList) {
            System.out.println(status.getCreatedAt()+ " " +status.getUser().getScreenName()+ " ");
            URLEntity[] urls = status.getURLEntities();
            for(URLEntity urlEntity : urls) {
                System.out.println("Website : "+urlEntity.getExpandedURL());
                System.out.println("Website : "+urlEntity.getURL());
            }
        }
    }

    public void printFavourites(Twitter twitter) throws TwitterException {
        List<Status> statuses = twitter.getFavorites();
        for (Status status : statuses) {
            System.out.println(status);
        }
    }

    public void printFollowersList (Twitter twitter, String userName) throws TwitterException {
        List<User> followers = twitter.getFollowersList(userName, -1);
        int i=1;
        for (User follower : followers) {
            System.out.println(i+follower.getName() +" "+ follower.getId());
            i++;
        }

    }
    public List<Long> getFollowersIDList (Twitter twitter, String userName) throws TwitterException {
        long cursor =-1L;
        IDs ids;
        List<Long> followersIDList = new ArrayList<>();
        do {
            ids = twitter.getFollowersIDs(userName, cursor);
            for(long userID : ids.getIDs()){
                followersIDList.add(userID);
            }
        } while((cursor = ids.getNextCursor())!=0 );
        return  followersIDList;
    }

    public void printFollowersIDList (Twitter twitter, String userName) throws TwitterException {
        List<Long> followersIDList = getFollowersIDList(twitter, userName);
        for(long userID: followersIDList){
            System.out.println(userID);
            //twitter.showUser(userID).getScreenName();
        }
    }

    public Integer countFollowers (Twitter twitter, String userName) throws TwitterException {
        List<Long> followersIDList = getFollowersIDList(twitter, userName);
        Integer followersCount = followersIDList.size();
        System.out.println(followersCount);
        return followersCount;
    }

    public void printRetweets(Twitter twitter, Long tweetID )throws TwitterException {
        List<Status> statuses = twitter.getRetweets(tweetID);
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getId());
        }
    }
    public List<Long> getRetweeterIDList(Twitter twitter, Long tweetID )throws TwitterException {

        long cursor =-1L;
        IDs ids;
        List<Long> retweeterIDList = new ArrayList<>();
        do {
            ids = twitter.getRetweeterIds(tweetID,200, cursor);
            for(long userID : ids.getIDs()){
                retweeterIDList.add(userID);
            }
        } while((cursor = ids.getNextCursor())!=0 );
        return  retweeterIDList;
    }

    public void printRetweeterIDList(Twitter twitter, Long tweetID )throws TwitterException {
        List<Long> retweeterIDList = getRetweeterIDList(twitter, tweetID);
        for(long userID: retweeterIDList){
            System.out.println(userID);
        }
    }
    public Integer countRetweets (Twitter twitter, Long tweetID ) throws TwitterException {
        List<Long> retweeterIDList = getRetweeterIDList(twitter, tweetID);
        Integer retweetsCount = retweeterIDList.size();
        System.out.println(retweetsCount);

        return retweetsCount;
    }
    public Integer countRetweets2 (Twitter twitter, Long tweetID ) throws TwitterException {
        Integer retweetsCount = twitter.showStatus(tweetID).getRetweetCount();
        System.out.println(retweetsCount);
        return retweetsCount;
    }

    public void printUser(Twitter twitter,String userName) throws TwitterException {
        User user = twitter.showUser(userName);
        System.out.println("id "+user.getId());
        System.out.println("name "+ user.getName());
        System.out.println("screename " + user.getScreenName());
        System.out.println("created " +user.getCreatedAt());
        System.out.println("lang " +user.getLang());
        System.out.println("url " +user.getURL());
        System.out.println("urlEntity " +user.getURLEntity().getExpandedURL());
        System.out.println("description " +user.getDescription());
        System.out.println("statuses " +user.getStatusesCount());
        System.out.println("favourites " +user.getFavouritesCount());
        System.out.println("followers " +user.getFollowersCount());
        System.out.println("friends " +user.getFriendsCount());
        System.out.println("listed " +user.getListedCount());
        System.out.println("location " +user.getLocation());
        System.out.println("defaultImage " +user.isDefaultProfileImage());
        System.out.println("defaultProfile " +user.isDefaultProfile());
        String url= "https://twitter.com/" + user.getScreenName();
        System.out.println(url);
        URLEntity[] urls = user.getDescriptionURLEntities();
        for(URLEntity urlEntity : urls) {
            System.out.println("Website : "+urlEntity.getExpandedURL());
            // System.out.println("Website : "+urlEntity.getURL());
        }

    }

    }
