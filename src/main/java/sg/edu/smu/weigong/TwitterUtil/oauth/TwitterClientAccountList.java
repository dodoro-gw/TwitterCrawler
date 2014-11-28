package sg.edu.smu.weigong.TwitterUtil.oauth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClientAccountList {
	public  static List<OAuth> accountList;
	public static Integer active;
	
	
	//get the accounts from Twitter Rest API
	//account is in JSON format
	public static String account0 = "{\"consumer_secret\":\"\",\"consumer_key\":\"\",\"token\":\"\",\"token_secret\":\"\"}";
	public static String account1 = "";
	public static String account2 = "";
	public static String account3 = "";
	public static String account4 = "";
	public static String account5 = "";
	public static String account6 = "";
	public static String account7 = "";
	public static String account8 = "";
	public static String account9 = "";
	public static String account10 = "";
	public static String account11 = "";
	public static String account12 = "";
	public static String account13 = "";
	public static String account14 = "";
	public static String account15 = "";
	public static String account16 = "";
	public static String account17 = "";
	public static String account18 = "";
	public static String account19 = "";
	public static String account20 = "";
	public static String account21 = "";
	public static String account22 = "";
	public static String account23 = "";
	public static String account24 = "";
	public static String account25 = "";
	public static String account26 = "";
	public static String account27 = "";
	public static String account28 = "";
	public static String account29 = "";

	
	/*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&remember to change the number of accounts*/
	public static int numOfAccounts = 30;
	
	static{
		List<OAuth> accountListTemp = new ArrayList<OAuth>();
		OAuth auth0 = new OAuth(account0, 0);
		OAuth auth1 = new OAuth(account1, 1);
		OAuth auth2 = new OAuth(account2, 2);
		OAuth auth3 = new OAuth(account3, 3);
		OAuth auth4 = new OAuth(account4, 4);
		OAuth auth5 = new OAuth(account5, 5);
		OAuth auth6= new OAuth(account6, 6);
		OAuth auth7 = new OAuth(account7, 7);
		OAuth auth8= new OAuth(account8, 8);
		OAuth auth9 = new OAuth(account9, 9);
		OAuth auth10 = new OAuth(account10, 10);
		OAuth auth11 = new OAuth(account11, 11);
		OAuth auth12 = new OAuth(account12, 12);
		OAuth auth13 = new OAuth(account13, 13);
		OAuth auth14 = new OAuth(account14, 14);
		
		OAuth auth15 = new OAuth(account15, 15);
		OAuth auth16 = new OAuth(account16, 16);
		OAuth auth17 = new OAuth(account17, 17);
		OAuth auth18 = new OAuth(account18, 18);
		OAuth auth19 = new OAuth(account19, 19);
		OAuth auth20 = new OAuth(account20, 20);
		OAuth auth21= new OAuth(account21, 21);
		OAuth auth22 = new OAuth(account22, 22);
		OAuth auth23= new OAuth(account23, 23);
		OAuth auth24 = new OAuth(account24, 24);
		OAuth auth25 = new OAuth(account25, 25);
		OAuth auth26 = new OAuth(account26, 26);
		OAuth auth27 = new OAuth(account27, 27);
		OAuth auth28 = new OAuth(account28, 28);
		OAuth auth29 = new OAuth(account29, 29);

		accountListTemp.add(auth0);
		accountListTemp.add(auth1);
		accountListTemp.add(auth2);
		accountListTemp.add(auth3);
		accountListTemp.add(auth4);
		accountListTemp.add(auth5);
		accountListTemp.add(auth6);
		accountListTemp.add(auth7);
		accountListTemp.add(auth8);
		accountListTemp.add(auth9);
		accountListTemp.add(auth10);
		accountListTemp.add(auth11);		
		accountListTemp.add(auth12);
		accountListTemp.add(auth13);
		accountListTemp.add(auth14);
		
		accountListTemp.add(auth15);
		accountListTemp.add(auth16);
		accountListTemp.add(auth17);
		accountListTemp.add(auth18);
		accountListTemp.add(auth19);
		accountListTemp.add(auth20);
		accountListTemp.add(auth21);		
		accountListTemp.add(auth22);
		accountListTemp.add(auth23);
		accountListTemp.add(auth24);
		accountListTemp.add(auth25);
		accountListTemp.add(auth26);
		accountListTemp.add(auth27);
		accountListTemp.add(auth28);
		accountListTemp.add(auth29);

		accountList = Collections.unmodifiableList(accountListTemp);

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
		.setOAuthConsumerKey(accountList.get(a).consumer_key)
		.setOAuthConsumerSecret(accountList.get(a).consumer_key_secret)
		.setOAuthAccessToken(accountList.get(a).token)
		.setOAuthAccessTokenSecret(accountList.get(a).token_secret);
		cb.setIncludeEntitiesEnabled(true);		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;
	}
	
}
