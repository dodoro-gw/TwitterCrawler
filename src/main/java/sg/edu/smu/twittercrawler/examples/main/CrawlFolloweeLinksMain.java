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
import twitter4j.TwitterException;
import twitter4j.User;

public class CrawlFolloweeLinksMain {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
        String userIdFile = "/Users/sunflower/Dropbox/Daily Records/hidden topic interest/data/seedUserFolloweeIdsList_tail.csv";
        String outputFolloweeLinkFile = "/Users/sunflower/Dropbox/Daily Records/hidden topic interest/data/testUserFolloweeLink.csv";
        String oAuthAccountsFile = "/accounts.json";

        if (args.length == 2) {
            userIdFile = args[0];
            outputFolloweeLinkFile = args[1];
            oAuthAccountsFile = args[2];
        }

        //load user id list
        long[] usersList = UserIdReader.getUserList(userIdFile);

        //output file
        PrintWriter followeeWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolloweeLinkFile)));

        //new crawler
        TwitterCrawler crawler = TwitterCrawler.createInstance(OAuthAccountsLoader.load(oAuthAccountsFile));


        //At each time, look up 100 users and crawl their follow links
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

            if (users != null) for (User user : users) {
                boolean isProtected = user.isProtected();
                if (isProtected == true) {
                    continue;
                }
                System.out.println("User: @" + user.getId());
                followeeWriter.print(user.getId() + ",");
                List<Long> ids = crawler.crawlUserFolloweeLinks(user);
                StringBuilder followeeIds = new StringBuilder();
                for (long id : ids) {
                    followeeIds.append(id + "-");
                }
                if (followeeIds.length() >= 1) {
                    followeeIds.setLength(followeeIds.length() - 1);
                }
                followeeWriter.println(followeeIds.toString());
            }
            followeeWriter.flush();
        }
        followeeWriter.close();

    }

}
