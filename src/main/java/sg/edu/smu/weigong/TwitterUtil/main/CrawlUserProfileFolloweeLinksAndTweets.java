package sg.edu.smu.weigong.TwitterUtil.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import sg.edu.smu.weigong.TwitterUtil.crawler.CrawlerForOneUser;
import sg.edu.smu.weigong.TwitterUtil.oauth.TwitterClientAccountList;
import sg.edu.smu.weigong.TwitterUtil.util.TextUtil;
import sg.edu.smu.weigong.TwitterUtil.util.TwitterUtil;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlUserProfileFolloweeLinksAndTweets {

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
		
		//set up for twitter
		Twitter  twitter = TwitterClientAccountList.createTwitterClient(2);
		
		//output file
		PrintWriter profileWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputUserProfileFile)));
		PrintWriter followWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolloweeLinkFile)));
		
		CrawlerForOneUser.setTwitter(twitter);

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
			
			ResponseList<User> users = null;
			while(users == null){
				try{
					users = twitter.lookupUsers(subUsersList);
				}catch(Exception e){
					twitter = TwitterUtil.checkAndWait(twitter);
				}
			}
			
			for (User user : users) {
				CrawlerForOneUser.setUser(user);
		    	System.out.println("User: @" + user.getId());
		    	
		    	//output profile
		    	profileWriter.println(CrawlerForOneUser.crawlUserProfile());
		    	
				boolean isProtected = user.isProtected();
		    	if(isProtected == true){
		    		continue;
		    	}

		    	//output followees
		    	followWriter.print(user.getId()+",");
		    	followWriter.println(CrawlerForOneUser.crawlUserFolloweeLinks());
		    	
		    	//output tweets
		    	PrintWriter tweetWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputTweetsFolder+"_"+user.getId())));
		    	tweetWriter.println(CrawlerForOneUser.crawlUserTweets(5));
		    	tweetWriter.close();
			}
			profileWriter.flush();
			followWriter.flush();
		}
		profileWriter.close();
		followWriter.close();
	}

}
