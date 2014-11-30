package sg.edu.smu.twittercrawler.core.util;
import java.util.Map;

import sg.edu.smu.twittercrawler.core.oauth.OAuthAccountsSelector;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterUtil {
	
	//check if current twitter account is available. If not, then use next twitter account
	public static Twitter checkAndWait(Twitter twitter) throws InterruptedException{
		try {
			Map<String ,RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
			for (String endpoint : rateLimitStatus.keySet()) {
			    RateLimitStatus status = rateLimitStatus.get(endpoint);

				if(status.getRemaining() == 0){
					System.out.println(OAuthAccountsSelector.active);
					System.out.println("Hit limit reached for "+endpoint+" api call. Next quota available in " + status.getSecondsUntilReset() + " sec.");
					twitter = OAuthAccountsSelector.chooseAnotherOAuthAccount();
					waitUntilAvailable(twitter);
					return twitter;
				}
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Error when check and wait");
		}
		return twitter;
	}
	
	//Check if API Limit Available
	public static boolean isLimitAvailable(Twitter twitter)
			throws TwitterException, InterruptedException {
		try {
	
			Map<String ,RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
			for (String endpoint : rateLimitStatus.keySet()) {
			    RateLimitStatus status = rateLimitStatus.get(endpoint);

				if(status.getRemaining() == 0){
					System.out.println(OAuthAccountsSelector.active);
					System.out.println("Hit limit reached for "+endpoint+" api call. Next quota available in " + status.getSecondsUntilReset() + " sec.");
					return false;
				}
			}
			return true;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	// wait untill available
	public static void waitUntilAvailable(Twitter twitter)
			throws TwitterException, InterruptedException {
		try {
			Map<String ,RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
			for (String endpoint : rateLimitStatus.keySet()) {
			    RateLimitStatus status = rateLimitStatus.get(endpoint);

				if(status.getRemaining() == 0){
					System.out.println("Hit limit reached for "+endpoint+" api call. Next quota available in " + status.getSecondsUntilReset() + " sec.");
					Thread.sleep((status.getSecondsUntilReset()+10) * 1000);
				}
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	}
	
	// written by nelman
/*	public static boolean isAPILimitAvail(Twitter t, String resourceName){
		if(t == null){
			System.out.println("Twitter api is not specified.");
			return false;
		}
		try {
			//	RateLimitStatus rateLimit = t.getRateLimitStatus();
			Map<String,RateLimitStatus> rateLimit = t.getRateLimitStatus(resourceName);
			if(rateLimit != null){
				for(String resource : rateLimit.keySet()){
					RateLimitStatus rlt = rateLimit.get(resource);
					if(rlt.getRemaining() == 0){
						System.out.println("Hit limit reached for "+resource+" api call. Next quota available in " + rlt.getSecondsUntilReset() + " sec.");
						return false;
						//Thread.sleep(rlt.getSecondsUntilReset() * 1000);
					}else{
						return true;
					}
				}
			}else{
				System.out.println("Unknown resource point "+resourceName+" to check API limit");
				return false;
			}
			return true;
		} catch (TwitterException e) {
			System.out.println(e.getErrorCode()+"-"+e.getErrorMessage());
			if(e.getErrorCode() == 88 || e.getErrorCode() == 63){
				System.out.println("Sleep for "+ e.getRateLimitStatus().getSecondsUntilReset() +" sec.");
				if(e.getRateLimitStatus().getSecondsUntilReset() > 0){
					return false;
					//Thread.sleep(e.getRateLimitStatus().getSecondsUntilReset() * 1000);
				}
			}
			return false;
		} 

	}

	public static long showUserID(Twitter t, String screenName){
		if(t == null)
			return -1;
		try{
			if(isAPILimitAvail(t,"users")){
				User u = t.showUser(screenName);
				if(u != null){
					if(u.isProtected()){
						System.out.println(screenName + " is a protected account");
						return -1;
					}
					System.out.println(screenName + " user id is "+ u.getId());
					return u.getId();
				}
			}
		}catch(TwitterException e){
			e.printStackTrace();
		}
		return -1;
	}

	public static User showUser(Twitter t, String screenName){
		if(t == null)
			return null;
		try{
			if(isAPILimitAvail(t,"users")){
				User u = t.showUser(screenName);
				if(u != null){
					return u;
				}
			}


		}catch(TwitterException e){
			e.printStackTrace();
		}
		return null;
	}
	

	
	public static Twitter createClient(String consumerKey, String consumerSecret, String token, String tokenSecret){
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(token)
		.setOAuthAccessTokenSecret(tokenSecret);
		cb.setIncludeEntitiesEnabled(true);		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;
	}*/
}
