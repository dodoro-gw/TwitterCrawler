package sg.edu.smu.sis.twittercrawler.crawler;

import sg.edu.smu.sis.twittercrawler.task.AbstractTask;
import twitter4j.ResponseList;
import twitter4j.User;

import java.util.List;

public class CrawlerProcessor {

    private static final int USER_LOOKUP_LIMIT = 100;

    private Crawler crawler;

    private List<AbstractTask> tasks;

    public CrawlerProcessor(Crawler crawler, List<AbstractTask> tasks) {
        this.crawler = crawler;
        this.tasks = tasks;
    }

    public void run(List<Long> userIds) {
        for (int cursor = 0; cursor < userIds.size(); cursor += USER_LOOKUP_LIMIT) {
            if (cursor + USER_LOOKUP_LIMIT < userIds.size()) {
                this.processBatch(userIds.subList(cursor, cursor + USER_LOOKUP_LIMIT));
            } else {
                this.processBatch(userIds.subList(cursor, userIds.size()));
            }
        }
    }

    private void processBatch(List<Long> userIds) {
        long[] _userIds = new long[userIds.size()];
        int i = 0;
        for (Long userId : userIds) {
            _userIds[i] = userId;
            i++;
        }

        ResponseList<User> users = crawler.crawlResponseUsers(_userIds);

        for (User user : users) {
            for (AbstractTask task : this.tasks) {
                task.execute(user, crawler);
            }
        }
    }

}
