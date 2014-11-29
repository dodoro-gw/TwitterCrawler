package sg.edu.smu.weigong.TwitterUtil.task;

import java.io.PrintWriter;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import twitter4j.TwitterException;

public class CrawlUserProfileTask extends AbstractTask {
	public CrawlUserProfileTask() {
	}
	
	public CrawlUserProfileTask(PrintWriter w){
		this.writer = w;
	}
	
	
	public void execute(Crawler crawler) throws TwitterException, InterruptedException{
		setResult(crawler.crawlUserProfile(this.getUser()));
	}
}
