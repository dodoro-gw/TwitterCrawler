package sg.edu.smu.weigong.TwitterUtil.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import twitter4j.TwitterException;

/**
 * 
 * @author weigong
 *
 */
public class CrawlUserTweetsTask extends AbstractTask {

	public CrawlUserTweetsTask() {
	}
	
	public CrawlUserTweetsTask(String folder){
		outputTweetsFolder = folder;
	}
	
	private Integer numOfPages;

	private String outputTweetsFolder;

	public Integer getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(Integer numOfPages) {
		this.numOfPages = numOfPages;
	}

	public void execute(Crawler crawler) throws TwitterException,
			InterruptedException {
		setResult(crawler.crawlUserTweets(this.getUser(), numOfPages));
	}

	public void output() throws IOException {
		// TODO
		writer = new PrintWriter(new BufferedWriter(new FileWriter(
				outputTweetsFolder + "_" + this.getUser().getId())));
		writer.println(this.result);
		writer.close();
	}

	public String getOutputTweetsFolder() {
		return outputTweetsFolder;
	}

	public void setOutputTweetsFolder(String outputTweetsFolder) {
		this.outputTweetsFolder = outputTweetsFolder;
	}

}
