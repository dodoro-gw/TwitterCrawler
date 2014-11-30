package sg.edu.smu.sis.twittercrawler.task;

import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.*;

public abstract class AbstractTask {

    private OutputStrategy outputStrategy;

    public void execute(User user, Crawler crawler) {
        String result = null;
        try {
            result = this.crawl(user, crawler);
            outputStrategy.output(user, result);
        } catch (TwitterException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String crawl(User user, Crawler crawler) throws TwitterException, InterruptedException;

    public OutputStrategy getOutputStrategy() {
        return outputStrategy;
    }

    public void setOutputStrategy(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

}
