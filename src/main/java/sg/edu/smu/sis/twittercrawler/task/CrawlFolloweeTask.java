package sg.edu.smu.sis.twittercrawler.task;

import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlFolloweeTask extends AbstractTask {

    @Override
    protected String crawl(User user, Crawler crawler) throws TwitterException, InterruptedException {
        return crawler.crawlUserFolloweeLinks(user);
    }

}
