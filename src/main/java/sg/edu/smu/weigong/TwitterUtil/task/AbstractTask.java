package sg.edu.smu.weigong.TwitterUtil.task;

import java.io.IOException;
import java.io.PrintWriter;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import twitter4j.TwitterException;
import twitter4j.User;

public abstract class AbstractTask {
	
	private User user;

	protected String result;
	
	protected PrintWriter writer;
	
	public AbstractTask() {
	}
	
	public AbstractTask(PrintWriter w){
		this.writer = w;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	protected void setResult(String result) {
		this.result = result;
	}
	
	public abstract void execute(Crawler crawler) throws TwitterException, InterruptedException;
	
	public  void output() throws IOException {		
		writer.println(this.result);
	}
}
