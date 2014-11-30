package sg.edu.smu.twittercrawler.examples.main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import sg.edu.smu.twittercrawler.core.oauth.OAuthAccountsLoader;
import sg.edu.smu.twittercrawler.core.util.UserIdReader;
import sg.edu.smu.twittercrawler.core.TwitterCrawler;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlUserTweetsMain {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, TwitterException {
		String userIdFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\seedUserFolloweeIdsList.csv";
		String outputTweetsFolder = "E:\\hidden topic interest\\followeeTweets\\";
		String oAuthAccountsFile = "/accounts.json";
		Integer numOfTweets = 200;

		if(args.length == 4){
			userIdFile = args[0];
			outputTweetsFolder = args[1];
			oAuthAccountsFile = args[2];
			numOfTweets = Integer.valueOf(args[3]);
		}

		//load user id list
		long[] usersList = UserIdReader.getUserList(userIdFile);

		//new crawler
		TwitterCrawler crawler = TwitterCrawler.createInstance(OAuthAccountsLoader.load(oAuthAccountsFile));


		//At each time, look up 100 users and crawl their tweets
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
					boolean isProtected = user.isProtected();
			    	if(isProtected == true){
			    		continue;
			    	}
			    	System.out.println("User: @" + user.getScreenName() + "\t" + user.getId());
			    	PrintWriter tweetWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputTweetsFolder+"_"+user.getId())));
			    	List<Status> tweets = crawler.crawlUserTweets(user, numOfTweets);
					for(Status s: tweets){
						String tweet = s.getText().replaceAll("\n|,|\r", ".");
						tweetWriter.println(user.getId() + "," + s.getId() + ","
								+ s.getCreatedAt().toString() + "," + tweet + "\n");
					}
			    	tweetWriter.close();
				}
			}
		}
		
	}

}
