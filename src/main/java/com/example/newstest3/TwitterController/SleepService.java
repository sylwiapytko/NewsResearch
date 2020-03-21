package com.example.newstest3.TwitterController;

import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;

@Service
public class SleepService {

    public void sleepForTime(Integer time){//time 1000
        try {
            sleep(time); // 900 rqt / 15 mn <=> 1 rqt/s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
