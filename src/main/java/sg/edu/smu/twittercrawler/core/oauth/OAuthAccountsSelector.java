package sg.edu.smu.twittercrawler.core.oauth;

import twitter4j.*;
import twitter4j.conf.*;

import java.util.*;

public class OAuthAccountsSelector {

	private List<OAuthAccount> accountList;

	private Integer active;

	private Integer numOfAccounts;

	public OAuthAccountsSelector(List<OAuthAccount> accountList){
		this.accountList = accountList;
		active = null;
		numOfAccounts = accountList.size();
	}

	private int nextAccount(){
		if(active < numOfAccounts-1){
			return active+1;
		}else{
			return 0;
		}
	}

	public Twitter chooseAnotherOAuthAccount(){
		int next = nextAccount();
		return createTwitterClient(next);
	}

	public Twitter createTwitterClient(int a){
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


	public List<OAuthAccount> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<OAuthAccount> accountList) {
		this.accountList = accountList;
		this.numOfAccounts = accountList.size();
	}

}
