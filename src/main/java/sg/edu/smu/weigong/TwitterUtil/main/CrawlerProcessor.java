package sg.edu.smu.weigong.TwitterUtil.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import sg.edu.smu.weigong.TwitterUtil.task.AbstractTask;
import sg.edu.smu.weigong.TwitterUtil.task.CrawlFolloweeTask;
import sg.edu.smu.weigong.TwitterUtil.task.CrawlUserProfileTask;
import sg.edu.smu.weigong.TwitterUtil.task.CrawlUserTweetsTask;
import sg.edu.smu.weigong.TwitterUtil.util.TextUtil;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlerProcessor {
	
	public static void run(long[] userIds, Crawler crawler, List<AbstractTask> tasks) {	
		ResponseList<User> users;
		users = crawler.crawlResponseUsers(userIds);
		
		try {
			for(User user : users) {
				for(AbstractTask task: tasks){
					task.setUser(user);
					task.execute(crawler);
					task.output();
				}
			}
		} catch (TwitterException | InterruptedException | IOException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
		String userIdFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\seedUserFolloweeIdsList.csv";
		String outputUserProfileFile = "E:\\hidden topic interest\\followeeProfile.csv";
		String outputFolloweeLinkFile = "E:\\hidden topic interest\\followeeFolloweeLink.csv";
		String outputTweetsFolder = "E:\\hidden topic interest\\followeeTweets\\";
		if(args.length == 2){
			userIdFile = args[0];
			outputUserProfileFile = args[1];
			outputFolloweeLinkFile = args[2];
			outputTweetsFolder = args[3];
		}

		//load user id list
		long[] usersList = TextUtil.getUserList(userIdFile);
		
		//set up for crawler 
		Crawler crawler = new Crawler();
		
		//output file
		PrintWriter profileWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputUserProfileFile)));
		PrintWriter followeeWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolloweeLinkFile)));
		
		
		List<AbstractTask> tasks = new ArrayList<AbstractTask>();
		tasks.add(new CrawlUserTweetsTask(outputTweetsFolder));
		tasks.add(new CrawlFolloweeTask(followeeWriter));
		tasks.add(new CrawlUserProfileTask(profileWriter));
		
		
		//At each time, look up 99 users and crawl their follw links
		int numOfUsers = usersList.length; 
		int batch = (int) Math.ceil((double)numOfUsers/99); 
		for(int i = 0; i < batch; i++){
			long subUsersList[]; 
			if(i != batch-1){
				subUsersList = Arrays.copyOfRange(usersList, i*99, (i+1)*99);
			}else{
				subUsersList = Arrays.copyOfRange(usersList, i*99, numOfUsers);
			}
			
			run(subUsersList, crawler, tasks);
		
			profileWriter.flush();
			followeeWriter.flush();
		}
		profileWriter.close();
		followeeWriter.close();
	}

}
