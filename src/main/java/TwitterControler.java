import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

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
        List<Status> statuses = twitter.getUserTimeline("eitimagresea",paging);
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
        for (User follower : followers) {
            System.out.println(follower.getName() +" "+ follower.getId());
        }
    }

}
