import twitter4j.*;

import java.util.List;

/**
 * Created by Sylwia on 08-Dec-19.
 */
public class Application {
    public static void main(String[] args) throws TwitterException {

        System.out.println("Hello World");

        Twitter twitter = TwitterFactory.getSingleton();
        //String message="Hello to Java";
        //twitter.updateStatus(message);


        TwitterControler twitterControler = new TwitterControler();

        String userName = "chomik_slawomir";
        //twitterControler.printUserTimeline(twitter, "eitimagresea", 1, 10);
        //twitterControler.printURLfromMyTweets(twitter);
        //twitterControler.printFavourites(twitter);
        //twitterControler.printFollowersList(twitter,userName);
        //twitterControler.printFollowersIDList(twitter,userName);
        twitterControler.countFollowers(twitter,userName);

        System.out.println("End");

    }
}
