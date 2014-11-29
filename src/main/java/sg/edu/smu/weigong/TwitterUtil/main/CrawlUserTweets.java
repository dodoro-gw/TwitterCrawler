package sg.edu.smu.weigong.TwitterUtil.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import sg.edu.smu.weigong.TwitterUtil.crawler.Crawler;
import sg.edu.smu.weigong.TwitterUtil.util.TextUtil;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlUserTweets {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, TwitterException {
		String userIdFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\seedUserFolloweeIdsList.csv";
		String outputTweetsFolder = "E:\\hidden topic interest\\followeeTweets\\";

		if(args.length == 2){
			userIdFile = args[0];
			outputTweetsFolder = args[1];
		}

		//load user id list
		long[] usersList = TextUtil.getUserList(userIdFile);

		//new crawler
		Crawler crawler = new Crawler();
		
		
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
			
			ResponseList<User> users = crawler.crawlResponseUsers(subUsersList);
			
			if(users != null){
				for (User user : users) {
					boolean isProtected = user.isProtected();
			    	if(isProtected == true){
			    		continue;
			    	}
			    	System.out.println("User: @" + user.getScreenName() + "\t" + user.getId());
			    	PrintWriter tweetWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputTweetsFolder+"_"+user.getId())));
			    	tweetWriter.println(crawler.crawlUserTweets(user, 1));
			    	tweetWriter.close();
				}
			}
		}
		
	}

}
