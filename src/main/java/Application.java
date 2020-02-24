import twitter4j.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sylwia on 08-Dec-19.
 */
public class Application {
    public static void main(String[] args) throws TwitterException, InterruptedException, IOException {

        AppTwitterControler appTwitterControler = new AppTwitterControler();
        appTwitterControler.testTwitterController();

    }
}
