package com.example.newstest3.researchService;

import com.example.newstest3.entity.AccountClassification;
import com.example.newstest3.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExtraDataService {

    @Autowired
    Utils utils;
    @Autowired
    TweetService tweetService;

    public void fetchRetweetersofUsersbyClassificationandTime() {
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

    public void fetchAGAINRetweetersofUsersbyClassificationandTime() {
        String dateStartString = "03/04/2020";
        String dateEndTotalString = "11/04/2020";

        Date dateStart = utils.formatStringtoDate(dateStartString);
        Date dateEndTotal = utils.formatStringtoDate(dateEndTotalString);
        Date dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);

        while (dateEnd.compareTo(dateEndTotal) <= 0) {

            tweetService.fetchAGAINRetweetersofUsersbyClassificationandTime
                    (AccountClassification.MAINSTREAM, dateStart, dateEnd);
            dateStart = utils.addHoursToJavaUtilDate(dateStart, 1);
            dateEnd = utils.addHoursToJavaUtilDate(dateStart, 1);
        }
    }
}
