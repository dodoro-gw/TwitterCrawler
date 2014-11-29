package sg.edu.smu.sis.twittercrawler.crawler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import sg.edu.smu.sis.twittercrawler.util.UserIdReader;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlFolloweeLinks {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
		String userIdFile = "/Users/sunflower/Dropbox/Daily Records/hidden topic interest/data/seedUserFolloweeIdsList_tail.csv";
		//String outputTweetsFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\userTweets.csv";
		String outputFolloweeLinkFile = "/Users/sunflower/Dropbox/Daily Records/hidden topic interest/data/testUserFolloweeLink.csv";
		if(args.length == 2){
			userIdFile = args[0];
			//outputTweetsFile = args[1];
			outputFolloweeLinkFile = args[1];
		}

		//load user id list
		long[] usersList = UserIdReader.getUserList(userIdFile);
		
		//output file
		PrintWriter followWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolloweeLinkFile)));
		
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
			    	System.out.println("User: @" + user.getId());
			    	followWriter.print(user.getId()+",");
			    	//followWriter.print(CrawlerForOneUser.crawlUserFollowerLinks() + ",");
			    	followWriter.println(crawler.crawlUserFolloweeLinks(user));
				}
			}
			followWriter.flush();
		}
		followWriter.close();
		
	}

}
