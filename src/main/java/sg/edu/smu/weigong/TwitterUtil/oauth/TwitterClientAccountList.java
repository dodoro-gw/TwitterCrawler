package sg.edu.smu.weigong.TwitterUtil.oauth;

import twitter4j.*;
import twitter4j.conf.*;

import java.io.IOException;
import java.util.*;

public class TwitterClientAccountList {

	public  static List<OAuthAccount> accountList;

	public static Integer active;


	//get the accounts from Twitter Rest API
	//account is in JSON format
	//public static String account0 = "{\"consumer_secret\":\"\",\"consumer_key\":\"\",\"token\":\"\",\"token_secret\":\"\"}";



	/*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&remember to change the number of accounts*/
	public static int numOfAccounts;

	static{
		TwitterAccountsLoader loader = new TwitterAccountsLoader();
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		accountList = loader.getAccounts();
		numOfAccounts = accountList.size();
		active = null;
	}

	public static int nextAccount(){
		if(active < numOfAccounts-1){
			return active+1;
		}else{
			return 0;
		}
	}

	public static Twitter nextTwitterClient(){
		int next = nextAccount();
		return createTwitterClient(next);
	}

	public static Twitter createTwitterClient(int a){
		active = a;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(accountList.get(a).getConsumerKey())
		.setOAuthConsumerSecret(accountList.get(a).getConsumerSecret())
		.setOAuthAccessToken(accountList.get(a).getToken())
		.setOAuthAccessTokenSecret(accountList.get(a).getTokenSecret());
		cb.setIncludeEntitiesEnabled(true);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;
	}

}
