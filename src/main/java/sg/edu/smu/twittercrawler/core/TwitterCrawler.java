package sg.edu.smu.twittercrawler.core;

import sg.edu.smu.twittercrawler.core.oauth.OAuthAccount;
import sg.edu.smu.twittercrawler.core.oauth.OAuthAccountsSelector;
import sg.edu.smu.twittercrawler.core.util.TwitterUtil;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunflower on 11/30/14.
 */
public class TwitterCrawler {
    private Twitter twitter;
    private OAuthAccountsSelector oAuthAccountsSelector;

    public static final int USER_LOOKUP_LIMIT = 100;

    public static TwitterCrawler createInstance(List<OAuthAccount> oAuthAccounts) {
        if (oAuthAccounts == null || oAuthAccounts.size() < 1) {
            throw new IllegalArgumentException("Error: oAuthAccounts is null or size is 0.");
        }
        return new TwitterCrawler(oAuthAccounts);
    }

    private TwitterCrawler(List<OAuthAccount> oAuthAccounts) {
        // set up for twitter
        oAuthAccountsSelector = new OAuthAccountsSelector(oAuthAccounts);
        twitter = oAuthAccountsSelector.createTwitterClient(0);
    }

    public ResponseList<User> crawlResponseUsers(long[] subUsersList) {
        if(subUsersList.length > 100){
            throw new IllegalArgumentException("Error: each time, twitter api can only look up not more than " + USER_LOOKUP_LIMIT + " users. You can divide your user list into many sub user lists. Each sub user list can include up to 100 users.");
        }
        ResponseList<User> users = null;
        while (users == null) {
            try {
                users = twitter.lookupUsers(subUsersList);
            } catch (TwitterException e) {
                if (!handleReachRateLimitException(e)) {
                    return null;
                }
            }
        }
        return users;
    }

    public List<Status> crawlUserTweets(User user, int numOfTweets)
            throws TwitterException, InterruptedException {
        if (numOfTweets > 3200) {
            System.out
                    .println("Warning: Twitter API can return up to 3200 recent tweets from a user's timeline.");
            numOfTweets = 3200;
        }
        int numOfTweetsPerPage = 200;
        int numOfPages = numOfTweets / numOfTweetsPerPage;
        int remain = numOfTweets % numOfTweetsPerPage;
        if (remain > 0) {
            numOfPages++;
        }

        Paging paging = new Paging(1, numOfTweetsPerPage);
        List<Status> resultStatuses = new ArrayList<Status>();

        for (int i = 1; i <= numOfPages; i++) {
            paging.setPage(i);
            if (remain > 0 && i == numOfPages) {
                paging.setCount(remain);
            }


            List<Status> statuses = null;
            while (statuses == null) {
                try {
                    statuses = twitter.getUserTimeline(user.getScreenName(),
                            paging);
                } catch (TwitterException e) {
                    if (!handleReachRateLimitException(e)) {
                        return null;
                    }
                }
            }

            resultStatuses.addAll(statuses);
        }
        return resultStatuses;
    }


    public List<Long> crawlUserFollowerLinks(User user) throws TwitterException,
            InterruptedException {
        IDs ids;
        List<Long> resultIds = new ArrayList<Long>();

        long cursor = -1;
        do {
            ids = null;
            while (ids == null) {
                try {
                    ids = twitter.getFollowersIDs(user.getId(), cursor);
                } catch (TwitterException e) {
                    if (!handleReachRateLimitException(e)) {
                        return null;
                    }
                }
            }

            for (long id : ids.getIDs()) {
                resultIds.add(id);
            }
        } while ((cursor = ids.getNextCursor()) != 0);

        return resultIds;
    }

    public List<Long> crawlUserFolloweeLinks(User user) throws TwitterException,
            InterruptedException {
        IDs ids;
        List<Long> resultIds = new ArrayList<Long>();

        long cursor = -1;
        do {
            ids = null;
            while (ids == null) {
                try {
                    ids = twitter.getFriendsIDs(user.getId(), cursor);
                } catch (TwitterException e) {
                    if (!handleReachRateLimitException(e)) {
                        return null;
                    }
                }
            }
            if (ids != null) {
                for (long id : ids.getIDs()) {
                    resultIds.add(id);
                }
            }
        } while ((cursor = ids.getNextCursor()) != 0);

        return resultIds;
    }

    //return false if the exception does not because of reaching rate limit
    //return true otherwise
    private boolean handleReachRateLimitException(TwitterException e) {
        try {
            if (TwitterUtil.isLimitAvailable(twitter)) {
                e.printStackTrace();
                return false;
            } else {
                twitter = oAuthAccountsSelector.chooseAnotherOAuthAccount();
                TwitterUtil.waitUntilAvailable(twitter);
                return true;
            }
        } catch (TwitterException | InterruptedException e1) {
            throw new IllegalStateException(e);
        }
    }

}
