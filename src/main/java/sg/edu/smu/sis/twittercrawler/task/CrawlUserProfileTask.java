package sg.edu.smu.sis.twittercrawler.task;

import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import twitter4j.User;

public class CrawlUserProfileTask extends AbstractTask {

    @Override
    protected String crawl(User user, Crawler crawler) {
        return crawler.crawlUserProfile(user);
    }

}
