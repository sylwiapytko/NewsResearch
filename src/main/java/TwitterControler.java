import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

}
