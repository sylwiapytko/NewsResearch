package com.example.newstest3.TwitterController;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import static java.lang.Thread.sleep;
@Log
@Service
public class SleepService {

    public void printErrorAndSleepSec(TwitterException e, int seconds) {
        log.info("IM ALIVE & SLEEPING FOR "+ seconds + " seconds");
        e.printStackTrace();
        sleepForTime(seconds); //1000 = 1s
    }

    public void sleepForTime(Integer seconds){//time 1000
        int time = 1000 * seconds;
        try {
            sleep(time); // 900 rqt / 15 mn <=> 1 rqt/s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
