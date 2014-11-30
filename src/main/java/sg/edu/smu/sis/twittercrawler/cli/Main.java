package sg.edu.smu.sis.twittercrawler.cli;

import org.apache.commons.cli.*;
import sg.edu.smu.sis.twittercrawler.crawler.Crawler;
import sg.edu.smu.sis.twittercrawler.crawler.CrawlerProcessor;
import sg.edu.smu.sis.twittercrawler.task.AbstractTask;
import sg.edu.smu.sis.twittercrawler.task.CrawlTaskFactory;
import sg.edu.smu.sis.twittercrawler.task.CrawlTaskType;
import sg.edu.smu.sis.twittercrawler.task.FileOutputStrategy;
import sg.edu.smu.sis.twittercrawler.util.UserIdReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author taosun
 * @since 29/11/14
 */
public class Main {

    private static final String USER_IDS = "userIds";

    private static final String PROFILE = "profile";

    private static final String TWEETS = "tweets";

    private static final String FOLLOWERS = "followers";

    private static final String FOLLOWEES = "followees";

    private static final String HELP = "help";

    private static final Options options;

    static {
        options = new Options();
        options.addOption(OptionBuilder.isRequired().hasArg().withLongOpt(USER_IDS).withDescription("user ids").create());
        options.addOption(OptionBuilder.hasArg().withLongOpt(PROFILE).withDescription("crawl user profile").create());
        options.addOption(OptionBuilder.hasArg().withLongOpt(TWEETS).withDescription("crawl user tweets").create());
        options.addOption(OptionBuilder.hasArg().withLongOpt(FOLLOWERS).withDescription("crawl user followers").create());
        options.addOption(OptionBuilder.hasArg().withLongOpt(FOLLOWEES).withDescription("crawl user followees").create());
        options.addOption(OptionBuilder.withLongOpt(HELP).create());
    }

    public static void main(String[] args) {
        List<AbstractTask> tasks = new ArrayList<>();

        String userIdFile = null;
        String userProfileFile = null;
        String tweetsFile = null;
        String followersFile = null;
        String followeesFile = null;

        try {
            CommandLineParser parser = new BasicParser();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption(HELP)) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("crawler", options);
                return;
            }

            if (line.hasOption(USER_IDS)) {
                userIdFile = line.getOptionValue(USER_IDS);
            }
            if (line.hasOption(PROFILE)) {
                userProfileFile = line.getOptionValue(PROFILE);
                tasks.add(CrawlTaskFactory.createTask(CrawlTaskType.PROFILE,
                        new FileOutputStrategy(new File(userProfileFile))));
            }
            if (line.hasOption(TWEETS)) {
                tweetsFile = line.getOptionValue(TWEETS);
                tasks.add(CrawlTaskFactory.createTask(CrawlTaskType.TWEETS,
                        new FileOutputStrategy(new File(tweetsFile))));
            }
            if (line.hasOption(FOLLOWEES)) {
                followeesFile = line.getOptionValue(FOLLOWEES);
                tasks.add(CrawlTaskFactory.createTask(CrawlTaskType.TWEETS
                        , new FileOutputStrategy(new File(followeesFile))));
            }
            if (line.hasOption(FOLLOWERS)) {
                // TODO
                followersFile = line.getOptionValue(FOLLOWERS);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return;
        }

        List<Long> userIds;
        try {
            userIds = UserIdReader.getUsers(userIdFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        CrawlerProcessor processor = new CrawlerProcessor(new Crawler(), tasks);
        processor.run(userIds);
    }

}
