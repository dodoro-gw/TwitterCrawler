package sg.edu.smu.sis.twittercrawler.crawler;

import sg.edu.smu.sis.twittercrawler.task.AbstractTask;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.IOException;
import java.util.List;

public class CrawlerProcessor {

    private static final int USER_LOOKUP_LIMIT = 100;

    private Crawler crawler;

    public void run(List<Long> userIds, List<AbstractTask> tasks) {
        for (int cursor = 0; cursor < userIds.size(); cursor += USER_LOOKUP_LIMIT) {
            if (cursor + USER_LOOKUP_LIMIT < userIds.size()) {
                this.processBatch(userIds.subList(cursor, cursor + USER_LOOKUP_LIMIT), tasks);
            } else {
                this.processBatch(userIds.subList(cursor, userIds.size()), tasks);
            }
        }
    }

    private void processBatch(List<Long> userIds, List<AbstractTask> tasks) {
        long[] _userIds = new long[userIds.size()];
        int i = 0;
        for (Long userId : userIds) {
            _userIds[i] = userId;
            i++;
        }

        ResponseList<User> users = crawler.crawlResponseUsers(_userIds);
        try {
            for (User user : users) {
                for (AbstractTask task : tasks) {
                    task.setUser(user);
                    task.execute(crawler);
                    task.output();
                }
            }
        } catch (TwitterException | InterruptedException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    /*
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, TwitterException {
        String userIdFile = "C:\\Users\\wei.gong.2011\\Dropbox\\Daily Records\\hidden topic interest\\data\\seedUserFolloweeIdsList.csv";
        String outputUserProfileFile = "E:\\hidden topic interest\\followeeProfile.csv";
        String outputFolloweeLinkFile = "E:\\hidden topic interest\\followeeFolloweeLink.csv";
        String outputTweetsFolder = "E:\\hidden topic interest\\followeeTweets\\";
        if (args.length == 2) {
            userIdFile = args[0];
            outputUserProfileFile = args[1];
            outputFolloweeLinkFile = args[2];
            outputTweetsFolder = args[3];
        }

        //load user id list
        long[] usersList = UserIdReader.getUserList(userIdFile);

        //set up for crawler
        Crawler crawler = new Crawler();

        //output file
        PrintWriter profileWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputUserProfileFile)));
        PrintWriter followeeWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFolloweeLinkFile)));

        List<AbstractTask> tasks = new ArrayList<AbstractTask>();
        tasks.add(new CrawlUserTweetsTask(outputTweetsFolder));
        tasks.add(new CrawlFolloweeTask(followeeWriter));
        tasks.add(new CrawlUserProfileTask(profileWriter));

        //At each time, look up 99 users and crawl their follw links
        int numOfUsers = usersList.length;
        int batch = (int) Math.ceil((double) numOfUsers / 99);
        for (int i = 0; i < batch; i++) {
            long subUsersList[];
            if (i != batch - 1) {
                subUsersList = Arrays.copyOfRange(usersList, i * 99, (i + 1) * 99);
            } else {
                subUsersList = Arrays.copyOfRange(usersList, i * 99, numOfUsers);
            }

            run(subUsersList, crawler, tasks);

            profileWriter.flush();
            followeeWriter.flush();
        }
        profileWriter.close();
        followeeWriter.close();
    }
    */

}
