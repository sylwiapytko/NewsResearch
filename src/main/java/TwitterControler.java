import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void printUserTimeline(Twitter twitter, String userName, Integer pagingPage, Integer pagingCount) throws TwitterException {
        Paging paging = new Paging(pagingPage, pagingCount);
        List<Status> statuses = twitter.getUserTimeline(userName,paging);
        for (Status status : statuses) {
            System.out.println(status);
        }
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
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        }
    }
    public List<Long> getRetweeterIDList(Twitter twitter, Long tweetID )throws TwitterException {

        long cursor =-1L;
        IDs ids;
        List<Long> retweeterIDList = new ArrayList<>();
        do {
            ids = twitter.getRetweeterIds(tweetID, cursor);
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

    }
