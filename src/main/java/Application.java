import twitter4j.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sylwia on 08-Dec-19.
 */
public class Application {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {

        System.out.println("Hello World");

        Twitter twitter = TwitterFactory.getSingleton();
        //String message="Hello to Java";
        //twitter.updateStatus(message);


        TwitterControler twitterControler = new TwitterControler();

        String userName = "chomik_slawomir";
        //String userName = "oko_press";
        Long tweetId = 1222189367286026243L;
        //twitterControler.printUserTimeline(twitter, userName, 1, 10);
        //twitterControler.getUserTimelineList(twitter, userName);
        //System.out.println(twitter.showUser(userName).getStatusesCount());
        //twitterControler.printURLfromMyTweets(twitter);
        //twitterControler.printFavourites(twitter);
        //twitterControler.printFollowersList(twitter,userName);
        //twitterControler.printFollowersIDList(twitter,userName);
        //twitterControler.countFollowers(twitter,userName);
        //twitterControler.printRetweets(twitter,tweetId);
        //twitterControler.printRetweeterIDList(twitter,tweetId);
        //twitterControler.countRetweets(twitter,tweetId);

        //twitterControler.writeUserTimelineToFile(twitter,userName);




        System.out.println("End");

    }
}
