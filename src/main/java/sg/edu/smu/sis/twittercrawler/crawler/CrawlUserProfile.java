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

public class CrawlUserProfile {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
		String userIdFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\seedUserFolloweeIdsList.csv";
		String outputUserProfileFile = "E:\\hidden topic interest\\followeeProfile.csv";
		if(args.length == 2){
			userIdFile = args[0];
			outputUserProfileFile = args[1];
		}

		//load user id list
		long[] usersList = UserIdReader.getUserList(userIdFile);
		
		//output file
		PrintWriter profileWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputUserProfileFile)));
		
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
			    	System.out.println("User: @" + user.getId());
			    	profileWriter.println(crawler.crawlUserProfile(user));
				}
			}
		}
		profileWriter.close();
	}

}
