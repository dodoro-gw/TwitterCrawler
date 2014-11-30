package sg.edu.smu.twittercrawler.examples.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import sg.edu.smu.twittercrawler.core.oauth.OAuthAccountsLoader;
import sg.edu.smu.twittercrawler.core.util.UserIdReader;
import sg.edu.smu.twittercrawler.core.TwitterCrawler;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlUserProfileMain {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
		String userIdFile = "/seedUserFolloweeIdsList.csv";
		String outputUserProfileFile = "followeeProfile.csv";
		String oAuthAccountsFile = "/accounts.json";

		if(args.length == 2){
			userIdFile = args[0];
			outputUserProfileFile = args[1];
			oAuthAccountsFile = args[2];
		}

		//load user id list
		long[] usersList = UserIdReader.getUserList(userIdFile);
		
		//output file
		PrintWriter profileWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputUserProfileFile)));

		//new crawler
		TwitterCrawler crawler = TwitterCrawler.createInstance(OAuthAccountsLoader.load(oAuthAccountsFile));

		//At each time, look up 100 users and crawl their profile
		int numOfUsers = usersList.length;
		int numOfUserPerBatch = TwitterCrawler.USER_LOOKUP_LIMIT;
		int batch = (int) Math.ceil((double) numOfUsers / numOfUserPerBatch);
		for (int i = 0; i < batch; i++) {
			long subUsersList[];
			if (i != batch - 1) {
				subUsersList = Arrays.copyOfRange(usersList, i * numOfUserPerBatch, (i + 1) * numOfUserPerBatch);
			} else {
				subUsersList = Arrays.copyOfRange(usersList, i * numOfUserPerBatch, numOfUsers);
			}
			
			
			ResponseList<User> users = crawler.crawlResponseUsers(subUsersList);
			
			if(users != null){
				for (User user : users) {
			    	System.out.println("User: @" + user.getId());
			    	profileWriter.println(crawlUserProfile(user));
				}
			}
		}
		profileWriter.close();
	}

	public static String crawlUserProfile(User user) {
		String profile = null;

		boolean isProtected = user.isProtected();
		if (isProtected == true) {
			profile = user.getId() + ",PROTECTED";
			return profile;
		}
		int followerCount = user.getFollowersCount();
		int followeeCount = user.getFriendsCount();
		String createdAt = user.getCreatedAt().toString()
				.replaceAll(",|\n|\r", ";");
		String screemName = user.getScreenName();
		String name = user.getName().replaceAll(",|\n|\r", ";");
		String description = user.getDescription().replaceAll(",|\n|\r", ";");
		String location = user.getLocation().replaceAll(",|\n|\r", ";");

		profile = user.getId() + "," + screemName + "," + name + ","
				+ createdAt + "," + location + "," + followerCount + ","
				+ followeeCount + "," + description;
		return profile;
	}

}
