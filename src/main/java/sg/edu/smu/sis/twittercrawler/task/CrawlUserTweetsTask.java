package sg.edu.smu.sis.twittercrawler.task;

import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * @author weigong
 */
public class CrawlUserTweetsTask extends AbstractTask {

    private Integer numOfPages;

    public Integer getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
    }

    @Override
    protected String crawl(User user, Crawler crawler) throws TwitterException, InterruptedException {
        return crawler.crawlUserTweets(user, this.numOfPages);
    }
}
