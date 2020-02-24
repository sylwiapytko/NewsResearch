package com.example.newstest3.TwitterController;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class AppTwitterControler {

    public void testTwitterController() throws TwitterException {

        System.out.println("Hello World");


        Twitter twitter = TwitterFactory.getSingleton();
        //String message="Hello to Java";
        //twitter.updateStatus(message);

        TwitterControler twitterControler = new TwitterControler();

        //String userName = "eitimagresea";
        //String userName = "chomik_slawomir";
        //String userName = "oko_press";
        String userName = "hildatheseries";
        Long tweetId = 1228383913460092929L;
        //twitterControler.printUserTimeline(twitter, userName, 1, 2);
        //twitterControler.getUserTimelineList(twitter, userName);
        //System.out.println(twitter.showUser(userName).getStatusesCount());
        //twitterControler.printURLfromMyTweets(twitter);
        //twitterControler.printFavourites(twitter);
        //twitterControler.printFollowersList(twitter,userName);
        //twitterControler.printFollowersIDList(twitter,userName);
        //twitterControler.countFollowers(twitter,userName);
        //twitterControler.printRetweets(twitter,tweetId);
        // twitterControler.printRetweeterIDList(twitter,tweetId);
        //twitterControler.countRetweets(twitter,tweetId);
        //twitterControler.countRetweets2(twitter,tweetId);

        //twitterControler.writeUserTimelineToFile(twitter,userName);

        twitterControler.printUser(twitter,userName);


        System.out.println("End");
    }

}
