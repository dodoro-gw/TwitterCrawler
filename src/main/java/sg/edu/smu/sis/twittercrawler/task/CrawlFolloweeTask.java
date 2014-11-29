package sg.edu.smu.sis.twittercrawler.task;

import java.io.PrintWriter;

import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import twitter4j.TwitterException;

public class CrawlFolloweeTask extends AbstractTask {

	public CrawlFolloweeTask() {
	}
	
	public CrawlFolloweeTask(PrintWriter w){
		this.writer = w;
	}
	
	
	public void execute(Crawler crawler) throws TwitterException, InterruptedException{
		setResult(crawler.crawlUserFolloweeLinks(this.getUser()));
	}
	
}
