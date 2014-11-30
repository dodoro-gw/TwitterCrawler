package sg.edu.smu.sis.twittercrawler.task;

/**
 * TwitterCrawler
 *
 * @author taosun
 * @since 30/11/14
 */
public class CrawlTaskFactory {

    public static AbstractTask createTask(CrawlTaskType type, OutputStrategy outputStrategy) {
        AbstractTask task = null;

        switch (type) {
            case PROFILE:
                task = new CrawlUserProfileTask();
                break;
            case TWEETS:
                task = new CrawlUserTweetsTask();
                break;
            case FOLLOWEES:
                task = new CrawlFolloweeTask();
                break;
            default:
                break;
        }

        if(task == null) {
            throw new IllegalStateException("No task is created");
        }

        task.setOutputStrategy(outputStrategy);
        return task;
    }
}
