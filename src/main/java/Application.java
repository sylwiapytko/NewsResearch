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

        //twitterControler.printUserTimeline(twitter, "eitimagresea", 1, 10);
        //twitterControler.printURLfromMyTweets(twitter);
        //twitterControler.printFavourites(twitter);
        //twitterControler.printFollowersList(twitter,"chomik_slawomir");
        twitterControler.printFollowersIDList(twitter,"chomik_slawomir");

        System.out.println("End");

    }
}
