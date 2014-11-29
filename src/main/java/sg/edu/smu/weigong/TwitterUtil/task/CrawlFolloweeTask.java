package sg.edu.smu.weigong.TwitterUtil.task;

import java.io.PrintWriter;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import twitter4j.TwitterException;
import twitter4j.User;

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
