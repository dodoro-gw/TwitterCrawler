TwitterCrawler
==============
To crawl `Twitter` data for research use.

TwitterCrawler can crawl user profile, user followee/follower links, and user tweets given a list of twitter users' ID.

TwitterCrawler uses Twitter4j (http://twitter4j.org/en/index.html).

The main goal of TwitterCrawler is to speed up the crawling speed by using a list of Twitter application accounts. Once an account reaches rate limit, TwitterCrawler chooses another account to continue crawling the required data. 


Twitter Application accounts
=================
To use TwitterCrawler, you need to have at least one Twitter application account. It is better to have a list of accounts to fasten the crawling speed.

In order to get accounts from Twitter, see http://twitter.com/oauth_clients/new.

How to set the accounts, see http://twitter4j.org/en/code-examples.html (No.7 OAuth support).


