package sg.edu.smu.sis.twittercrawler.task;

import twitter4j.User;

import java.io.IOException;

/**
 * TwitterCrawler
 *
 * @author taosun
 * @since 30/11/14
 */
public interface OutputStrategy {

    public void output(User user, String content) throws IOException;

}
