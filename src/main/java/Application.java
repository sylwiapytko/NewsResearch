import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Created by Sylwia on 08-Dec-19.
 */
public class Application {
    public static void main(String[] args) throws TwitterException {

        System.out.println("Hello World");

        Twitter twitter = TwitterFactory.getSingleton();
        String message="Hello to Java";
        twitter.updateStatus(message);


        System.out.println("End");


    }
}
