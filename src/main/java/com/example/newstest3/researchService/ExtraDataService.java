package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.entity.Tweet;
import com.example.newstest3.repository.TweetRepository;
import com.example.newstest3.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExtraDataService {

    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;
    @Autowired
    TweetRepository tweetRepository;


    public void fetchRetweetersofUsersbyClassificationandTime() {
        String dateStartString = "22/03/2020 00:00";
        String dateEndTotalString = "11/04/2020 00:00";

        Date dateStart = utils.formatStringtoDate(dateStartString);
        Date dateEndTotal = utils.formatStringtoDate(dateEndTotalString);
        Date dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);

        List<Tweet> tweetListbig = tweetRepository.findTweetsByAccountClassificationAndTimeParamsAndZeroRetweetersFetched(AccountClassification.BIGMAINSTREAM, dateStart, dateEndTotal);
        List<Tweet> tweetListMS = tweetRepository.findTweetsByAccountClassificationAndTimeParamsAndZeroRetweetersFetched(AccountClassification.MAINSTREAM, dateStart, dateEndTotal);
        List<Tweet> tweetListJUNK = tweetRepository.findTweetsByAccountClassificationAndTimeParamsAndZeroRetweetersFetched(AccountClassification.JUNK, dateStart, dateEndTotal);
        System.out.println("bigms "+ tweetListbig.size() +" time " + tweetListbig.size()/3  +" min " + (tweetListbig.size()/3)/60 + " h" );
        System.out.println("ms "+ tweetListMS.size() +" time " + tweetListMS.size()/3  +" min " + (tweetListMS.size()/3)/60 + " h" );
        System.out.println("jk " + tweetListJUNK.size() + "time " + tweetListJUNK.size()/3 + " min " +(tweetListJUNK.size()/3)/60 + " h");
        while (dateEnd.compareTo(dateEndTotal) <= 0) {
            //tweetService.fetchRetweetersofUsersbyClassificationandTime(AccountClassification.BIGMAINSTREAM, dateStart, dateEnd);
            //tweetService.fetchRetweetersofUsersbyClassificationandTime(AccountClassification.MAINSTREAM, dateStart, dateEnd);
            tweetService.fetchRetweetersofUsersbyClassificationandTime(AccountClassification.JUNK, dateStart, dateEnd);
            dateStart = utils.addHoursToJavaUtilDate(dateStart, 1);
            dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);
        }
    }

    public void fetchAGAINRetweetersofUsersbyClassificationandTime() {
        String dateStartString = "03/04/2020";
        String dateEndTotalString = "11/04/2020";

        Date dateStart = utils.formatStringtoDate(dateStartString);
        Date dateEndTotal = utils.formatStringtoDate(dateEndTotalString);
        Date dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);

        while (dateEnd.compareTo(dateEndTotal) <= 0) {

            tweetService.fetchRetweetersofUsersbyClassificationandTime
                    (AccountClassification.MAINSTREAM, dateStart, dateEnd);
            dateStart = utils.addHoursToJavaUtilDate(dateStart, 1);
            dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);
        }
    }
}
