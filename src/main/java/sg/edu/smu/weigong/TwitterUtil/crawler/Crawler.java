package sg.edu.smu.weigong.TwitterUtil.crawler;

import java.util.List;

import sg.edu.smu.weigong.TwitterUtil.oauth.TwitterClientAccountList;
import sg.edu.smu.weigong.TwitterUtil.task.AbstractTask;
import sg.edu.smu.weigong.TwitterUtil.util.TwitterUtil;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class Crawler {

	private Twitter twitter;

	public Crawler() {
		// set up for twitter
		twitter = TwitterClientAccountList.createTwitterClient(10);
	}
	
	public void crawl(AbstractTask task) {
		User user = task.getUser();
		// TODO implement it
	}

	public ResponseList<User> crawlResponseUsers(long[] subUsersList) {
		ResponseList<User> users = null;
		while (users == null) {
			try {
				users = twitter.lookupUsers(subUsersList);
			} catch (TwitterException e) {
				try {
					if (TwitterUtil.isLimitAvailable(twitter)) {
						e.printStackTrace();
						break;
					} else {
						// twitter = TwitterUtil.checkAndWait(twitter);
						twitter = TwitterClientAccountList.nextTwitterClient();
						TwitterUtil.waitUntilAvailable(twitter);
					}
				} catch (TwitterException | InterruptedException e1) {
					throw new IllegalStateException(e1);
				}
			}
		}
		return users;
	}

	public String crawlUserProfile(User user) {
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

	public String crawlUserTweets(User user, int numOfPages)
			throws TwitterException, InterruptedException {
		Paging paging = new Paging(1, 200);
		List<Status> statuses;

		StringBuilder tweets = new StringBuilder();

		for (int i = 1; i <= numOfPages; i++) {
			paging.setPage(i);

			// twitter = TwitterUtil.checkAndWait(twitter);
			// statuses = twitter.getUserTimeline(user.getScreenName(), paging);

			statuses = null;
			while (statuses == null) {
				try {
					statuses = twitter.getUserTimeline(user.getScreenName(),
							paging);
				} catch (TwitterException e) {
					if (TwitterUtil.isLimitAvailable(twitter)) {
						e.printStackTrace();
						return null;
					} else {
						// twitter = TwitterUtil.checkAndWait(twitter);
						twitter = TwitterClientAccountList.nextTwitterClient();
						TwitterUtil.waitUntilAvailable(twitter);
					}
				}
			}

			for (Status s : statuses) {
				// System.out.println(s.getText());
				String tweet = s.getText().replaceAll("\n|,|\r", ".");
				tweets.append(user.getId() + "," + s.getId() + ","
						+ s.getCreatedAt().toString() + "," + tweet + "\n");
			}
		}
		return tweets.toString();
	}

	public String crawlUserFollowerLinks(User user) throws TwitterException,
			InterruptedException {
		IDs ids;
		StringBuilder followerIds = new StringBuilder();
		long cursor = -1;
		do {

			// twitter = TwitterUtil.checkAndWait(twitter);
			// ids = twitter.getFollowersIDs(user.getId(), cursor);

			ids = null;
			while (ids == null) {
				try {
					ids = twitter.getFollowersIDs(user.getId(), cursor);
				} catch (TwitterException e) {
					if (TwitterUtil.isLimitAvailable(twitter)) {
						e.printStackTrace();
						return null;
					} else {
						// twitter = TwitterUtil.checkAndWait(twitter);
						twitter = TwitterClientAccountList.nextTwitterClient();
						TwitterUtil.waitUntilAvailable(twitter);
					}
				}
			}
			if (ids != null) {
				for (long id : ids.getIDs()) {
					followerIds.append(id + "-");
				}
			}
		} while ((cursor = ids.getNextCursor()) != 0);
		if (followerIds.length() >= 1) {
			followerIds.setLength(followerIds.length() - 1);
		}
		return followerIds.toString();
	}

	public String crawlUserFolloweeLinks(User user) throws TwitterException,
			InterruptedException {
		IDs ids;
		StringBuilder followeeIds = new StringBuilder();
		long cursor = -1;
		do {
			// twitter = TwitterUtil.checkAndWait(twitter);
			// ids = twitter.getFriendsIDs(user.getId(), cursor);

			ids = null;
			while (ids == null) {
				try {
					ids = twitter.getFriendsIDs(user.getId(), cursor);
				} catch (TwitterException e) {
					if (TwitterUtil.isLimitAvailable(twitter)) {
						e.printStackTrace();
						return null;
					} else {
						// twitter = TwitterUtil.checkAndWait(twitter);
						twitter = TwitterClientAccountList.nextTwitterClient();
						TwitterUtil.waitUntilAvailable(twitter);
					}
				}
			}
			if (ids != null) {
				for (long id : ids.getIDs()) {
					followeeIds.append(id + "-");
				}
			}
		} while ((cursor = ids.getNextCursor()) != 0);
		if (followeeIds.length() >= 1) {
			followeeIds.setLength(followeeIds.length() - 1);
		}
		return followeeIds.toString();
	}

}
